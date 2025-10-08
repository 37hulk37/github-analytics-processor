package com.hulk.processor.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.data.elasticsearch.core.DocumentOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElasticSearchItemWriter implements ItemStreamWriter<RepositoryResult> {

    private final DocumentOperations operations;

    @Override
    public void write(@NonNull Chunk<? extends RepositoryResult> chunk) {
        operations.save(chunk);
    }
}
