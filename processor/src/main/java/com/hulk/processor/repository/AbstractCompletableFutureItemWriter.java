package com.hulk.processor.repository;

import com.hulk.processor.CompletableFutureCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;

import java.util.concurrent.CompletableFuture;

@Slf4j
public abstract class AbstractCompletableFutureItemWriter<V> extends AbstractItemWriter<V> {

    public <D> void writeWithCompletableFuture(Chunk<? extends CompletableFuture<V>> chunk) {
        chunk.getItems().stream()
            .collect(CompletableFutureCollector.allOf())
            .thenAccept(values -> {
                try {
                    write(new Chunk<>(values));
                } catch (Exception e) {
                    log.error("Can not write chunk", e);
                }
            });
    }

}
