package com.hulk.processor.repository.ml;

import com.hulk.processor.repository.Repository;
import lombok.SneakyThrows;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MlRepositoryProcessor implements ItemProcessor<Repository, CompletableFuture<Repository>> {

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    @Override
    @SneakyThrows
    public CompletableFuture<Repository> process(@NonNull Repository item) {
        return CompletableFuture.supplyAsync(() -> {
            // call python script
            return item;
        }, executor);
    }

}
