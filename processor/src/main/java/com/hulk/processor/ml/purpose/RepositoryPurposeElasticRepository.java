package com.hulk.processor.ml.purpose;

import com.hulk.processor.model.RepositoryPurposeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface RepositoryPurposeElasticRepository extends ElasticsearchRepository<RepositoryPurposeDocument, UUID> {
}
