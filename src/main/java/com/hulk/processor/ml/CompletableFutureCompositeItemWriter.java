package com.hulk.processor.ml;

import com.hulk.processor.repository.AbstractCompletableFutureItemWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class CompletableFutureCompositeItemWriter implements ItemWriter<CompletableFuture<MlMetrics>> {

    private final List<AbstractCompletableFutureItemWriter<MlMetrics>> delegates;

    @Override
    public void write(@NonNull Chunk<? extends CompletableFuture<MlMetrics>> chunk) {
        delegates.forEach(delegate ->
            delegate.writeWithCompletableFuture(chunk)
        );
    }
}
