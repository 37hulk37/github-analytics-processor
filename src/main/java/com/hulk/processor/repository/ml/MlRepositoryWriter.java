package com.hulk.processor.repository.ml;

import com.hulk.processor.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class MlRepositoryWriter implements ItemStreamWriter<CompletableFuture<Repository>> {

    @Override
    public void write(@NonNull Chunk<? extends CompletableFuture<Repository>> chunk) {
    }
}
