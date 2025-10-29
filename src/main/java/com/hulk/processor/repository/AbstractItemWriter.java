package com.hulk.processor.repository;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;
import java.util.function.Function;

public abstract class AbstractItemWriter<V> implements ItemWriter<V> {

    public <D> void write(
        Chunk<? extends V> chunk,
        Function<V, D> function,
        ElasticsearchRepository<D, UUID> repository
    ) {
        var entities = chunk.getItems().stream()
            .map(function)
            .toList();

        repository.saveAll(entities);
    }

}
