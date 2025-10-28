package com.hulk.processor.repository.supportduration;

import com.hulk.processor.model.SupportDurationDocument;
import com.hulk.processor.repository.AbstractRepositoryItemWriter;
import com.hulk.processor.repository.Repository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SupportDurationItemWriter extends AbstractRepositoryItemWriter {

    private final SupportDurationElasticRepository repository;

    @Override
    public void write(@NonNull Chunk<? extends Repository> chunk) {
        write(chunk, SupportDurationDocument::toDocument, repository);
    }
}
