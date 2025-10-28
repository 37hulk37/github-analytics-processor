package com.hulk.processor.ml;

import com.hulk.processor.model.MlMetrics;
import com.hulk.processor.repository.Repository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MlRepositoryProcessor implements ItemProcessor<Repository, CompletableFuture<MlMetrics>>, AutoCloseable {

    private final MlModel model;

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public CompletableFuture<MlMetrics> process(@NonNull Repository item) {
        return CompletableFuture.supplyAsync(() ->
            model.predict(item),
            executor
        );
    }

    @Override
    @SneakyThrows
    public void close() {
        model.close();

        boolean isTerminated = executor.isTerminated();
        if (isTerminated) {
            return;
        }

        while (!isTerminated) {
            isTerminated = executor.awaitTermination(30, TimeUnit.SECONDS);
        }
        executor.shutdownNow();
    }
}
