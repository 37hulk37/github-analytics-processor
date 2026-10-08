import argparse
import html
import json
import re
import time
from concurrent.futures import ThreadPoolExecutor, as_completed
from typing import Union

import requests

BASE_URL = "https://habr.com/kek/v2/articles/"

HEADERS = {
    "User-Agent": (
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
        "AppleWebKit/537.36 (KHTML, like Gecko) "
        "Chrome/124.0.0.0 Safari/537.36"
    ),
    "Accept": "application/json, text/plain, */*",
    "Accept-Language": "ru-RU,ru;q=0.9,en;q=0.8",
    "Referer": "https://habr.com/ru/search/",
}


def fetch_page(query: str, page: int, per_page: int = 20, timeout: int = 30, retries: int = 3) -> dict[str, Union[str, int]]:
    """Загружает одну страницу выдачи Хабра."""
    params = {
        "query": query,
        "order": "relevance",
        "fl": "ru",
        "hl": "ru",
        "page": page,
        "perPage": per_page,
    }

    last_error = None
    for attempt in range(1, retries + 1):
        try:
            response = requests.get(
                BASE_URL,
                params=params,
                headers=HEADERS,
                timeout=timeout,
            )
            response.raise_for_status()
            return response.json()
        except Exception as exc:
            last_error = exc
            if attempt < retries:
                time.sleep(1.5 * attempt)
            else:
                raise last_error


def get_publications(page_json: dict) -> list[dict[str, Union[str, dict]]]:
    """Возвращает список публикаций из ответа API."""
    refs = page_json.get("publicationRefs", {})
    return list(refs.values())


def strip_html(value: str) -> str:
    """Убирает HTML-теги и лишние пробелы."""
    if not value:
        return ""
    value = re.sub(r"<[^>]+>", "", value)
    value = html.unescape(value)
    value = re.sub(r"\s+", " ", value)
    return value.strip()


def make_token_patterns(tokens: list[str]) -> list[re.Pattern[str]]:
    """
    Делает regex-шаблоны для точного поиска слов.
    Например, 'go' не должен находиться внутри 'google'.
    """
    patterns = []
    for token in tokens:
        pattern = re.compile(
            r"(?<!\w)" + re.escape(token) + r"(?!\w)",
            re.IGNORECASE
        )
        patterns.append(pattern)
    return patterns


def article_matches(publication: dict, patterns: list[re.Pattern]) -> bool:
    """
    Проверяет, что все слова из запроса встречаются
    в заголовке или в лиде статьи.
    """
    title = strip_html(publication.get("titleHtml"))
    lead_html = publication.get("leadData", {}).get("textHtml", "")
    lead = strip_html(lead_html)

    haystack = f"{title}\n{lead}"

    return all(pattern.search(haystack) for pattern in patterns)


def publication_to_result(publication: dict, matched_tokens: list[str]) -> dict:
    """Преобразует публикацию Хабра в удобный для сохранения объект."""
    article_id = publication.get("id", "")
    post_type = publication.get("postType", "")

    if post_type == "news":
        url = f"https://habr.com/ru/news/{article_id}/"
    else:
        url = f"https://habr.com/ru/articles/{article_id}/"

    lead_html = publication.get("leadData", {}).get("textHtml", "")

    return {
        "id": article_id,
        "url": url,
        "timePublished": publication.get("timePublished"),
        "postType": post_type,
        "title": strip_html(publication.get("titleHtml") or publication.get("title")),
        "lead": strip_html(lead_html),
        "statistics": publication.get("statistics"),
        "hubs": [
            {
                "id": hub.get("id"),
                "alias": hub.get("alias"),
                "title": hub.get("title"),
            }
            for hub in publication.get("hubs", [])
        ],
        "tags": [
            tag.get("titleHtml")
            for tag in publication.get("tags", [])
        ],
        "matchedTokens": matched_tokens,
    }


def safe_filename(query: str) -> str:
    """Делает имя файла безопасным для Windows/Linux."""
    name = query.strip()
    name = re.sub(r'[\\/:*?"<>|]+', "_", name)
    name = re.sub(r"\s+", " ", name).strip()
    return name or "query"


def process_query(query: str, per_page: int = 20, max_workers: int = 8) -> list[dict]:
    tokens = [token for token in query.split() if token.strip()]
    if not tokens:
        raise ValueError("Пустой поисковый запрос")

    patterns = make_token_patterns(tokens)

    first_page = fetch_page(query, page=1, per_page=per_page)
    pages_count = int(first_page.get("pagesCount", 1))

    found = []

    for publication in get_publications(first_page):
        if article_matches(publication, patterns):
            found.append(publication_to_result(publication, tokens))

    if pages_count > 1:
        with ThreadPoolExecutor(max_workers=max_workers) as executor:
            futures = {
                executor.submit(fetch_page, query, page, per_page): page
                for page in range(2, pages_count + 1)
            }

            for future in as_completed(futures):
                page = futures[future]
                try:
                    page_json = future.result()
                except Exception as exc:
                    print(
                        f"[{query!r}] Ошибка при загрузке страницы {page}: {exc}"
                    )
                    continue

                for publication in get_publications(page_json):
                    if article_matches(publication, patterns):
                        found.append(publication_to_result(publication, tokens))

    unique = []
    seen = set()
    for item in found:
        article_id = item.get("id")
        if article_id and article_id not in seen:
            seen.add(article_id)
            unique.append(item)

    if len(unique) != 0:
        filename = f"{safe_filename(query)}.json"
        with open(filename, "w", encoding="utf-8") as file:
            json.dump(unique, file, ensure_ascii=False, indent=2)

        print(f"{query!r}: найдено {len(unique)} статей, сохранено в {filename}")
    else:
        print(f"{query!r}: статей не найдено")
    return unique


def main():
    parser = argparse.ArgumentParser(
        description="Поиск статей на Хабре по запросам через kek/v2 API."
    )
    parser.add_argument(
        "-q",
        "--query",
        action="append",
        required=True,
        help="Поисковый запрос. Можно указать несколько раз: -q 'Go 1.27' -q 'Zabbix API'",
    )
    parser.add_argument(
        "--max-workers",
        type=int,
        default=8,
        help="Количество потоков для загрузки страниц по одному запросу. По умолчанию 8.",
    )

    args = parser.parse_args()

    for query in args.query:
        try:
            process_query(
                query=query,
                max_workers=args.max_workers,
            )
        except Exception as exc:
            print(f"Ошибка при обработке запроса {query}: {exc}")


if __name__ == "__main__":
    main()
