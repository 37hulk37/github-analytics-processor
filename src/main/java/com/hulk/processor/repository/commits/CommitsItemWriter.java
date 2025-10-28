package com.hulk.processor.repository.commits;

import com.hulk.processor.model.CommitsDocument;
import com.hulk.processor.repository.AbstractRepositoryItemWriter;
import com.hulk.processor.repository.Repository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommitsItemWriter extends AbstractRepositoryItemWriter {

    private final CommitsElasticRepository repository;

    @Override
    public void write(@NonNull Chunk<? extends Repository> chunk) throws Exception {
        write(chunk, CommitsDocument::toDocument, repository);
    }
}
