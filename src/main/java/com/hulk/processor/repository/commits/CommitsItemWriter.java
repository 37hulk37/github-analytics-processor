package com.hulk.processor.repository.commits;

import com.hulk.processor.model.CommitsDocument;
import com.hulk.processor.repository.AbstractItemWriter;
import com.hulk.processor.repository.Repository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommitsItemWriter extends AbstractItemWriter<Repository> {

    private final CommitsElasticRepository repository;

    @Override
    public void write(@NonNull Chunk<? extends Repository> chunk) {
        write(chunk, CommitsDocument::toDocument, repository);
    }
}
