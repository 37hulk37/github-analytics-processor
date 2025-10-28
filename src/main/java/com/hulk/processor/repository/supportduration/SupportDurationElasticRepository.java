package com.hulk.processor.repository.supportduration;

import com.hulk.processor.model.SupportDurationDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface SupportDurationElasticRepository extends ElasticsearchRepository<SupportDurationDocument, UUID> {
}
