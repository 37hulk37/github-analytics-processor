package com.hulk.processor.repository.language;

import com.hulk.processor.model.LanguageDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface LanguageElasticRepository extends ElasticsearchRepository<LanguageDocument, UUID> {
}
