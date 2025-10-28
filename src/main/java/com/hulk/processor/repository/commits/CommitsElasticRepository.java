package com.hulk.processor.repository.commits;

import com.hulk.processor.model.CommitsDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface CommitsElasticRepository extends ElasticsearchRepository<CommitsDocument, UUID> {
}
