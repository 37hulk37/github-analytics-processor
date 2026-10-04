package com.hulk.processor.repository.collaborators;

import com.hulk.processor.model.CollaboratorsDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface CollaboratorsElasticRepository extends ElasticsearchRepository<CollaboratorsDocument, UUID> {
}
