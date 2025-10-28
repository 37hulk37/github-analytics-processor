package com.hulk.processor.ml;

import com.hulk.processor.CompletableFutureCollector;
import com.hulk.processor.model.MlMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class MlRepositoryWriter implements ItemStreamWriter<CompletableFuture<MlMetrics>> {

    private final MlMetricsElasticRepository repository;

    @Override
    public void write(@NonNull Chunk<? extends CompletableFuture<MlMetrics>> chunk) {
        chunk.getItems().stream()
            .collect(CompletableFutureCollector.allOf())
            .thenAccept(repository::saveAll);
    }
}
