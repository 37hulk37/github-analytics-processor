package com.hulk.processor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "repositories")
public record RepositoryEntity(
    @Id
    String id
) {
}
