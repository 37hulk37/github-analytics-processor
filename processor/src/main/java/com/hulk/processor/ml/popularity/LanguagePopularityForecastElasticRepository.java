package com.hulk.processor.ml.popularity;

import com.hulk.processor.model.LanguagePopularityForecastDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface LanguagePopularityForecastElasticRepository extends
    ElasticsearchRepository<LanguagePopularityForecastDocument, UUID> {
}
