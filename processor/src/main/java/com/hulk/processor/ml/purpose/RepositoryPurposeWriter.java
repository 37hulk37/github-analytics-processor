package com.hulk.processor.ml.purpose;

import com.hulk.processor.ml.MlMetrics;
import com.hulk.processor.model.RepositoryPurposeDocument;
import com.hulk.processor.repository.AbstractCompletableFutureItemWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepositoryPurposeWriter extends AbstractCompletableFutureItemWriter<MlMetrics> {

    private final RepositoryPurposeElasticRepository repository;

    @Override
    public void write(@NonNull Chunk<? extends MlMetrics> chunk) {
        write(chunk, RepositoryPurposeDocument::toDocument, repository);
    }
}
