package com.hulk.processor.repository.collaborators;

import com.hulk.processor.model.CollaboratorsDocument;
import com.hulk.processor.repository.AbstractItemWriter;
import com.hulk.processor.repository.Repository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CollaboratorsItemWriter extends AbstractItemWriter<Repository, CollaboratorsDocument> {

    private final CollaboratorsElasticRepository repository;

    @Override
    public void write(@NonNull Chunk<? extends Repository> chunk) {
        write(chunk, CollaboratorsDocument::toDocument, repository);
    }

}
