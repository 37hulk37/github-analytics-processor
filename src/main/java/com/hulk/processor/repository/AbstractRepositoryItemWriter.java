package com.hulk.processor.repository;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;
import java.util.function.Function;

public abstract class AbstractRepositoryItemWriter implements ItemWriter<Repository> {

    public <T> void write(
        Chunk<? extends Repository> chunk,
        Function<Repository, T> function,
        ElasticsearchRepository<T, UUID> repository
    ) {
        var entities = chunk.getItems().stream()
            .map(function)
            .toList();

        repository.saveAll(entities);
    }

}
