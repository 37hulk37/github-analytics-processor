package com.hulk.processor.ml;

import com.hulk.processor.model.MlMetrics;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MlMetricsElasticRepository extends ElasticsearchRepository<MlMetrics, String> {
}
