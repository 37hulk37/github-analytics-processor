package com.hulk.processor.repository.language;

import com.hulk.processor.model.LanguageDocument;
import com.hulk.processor.repository.AbstractRepositoryItemWriter;
import com.hulk.processor.repository.Repository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LanguageItemWriter extends AbstractRepositoryItemWriter {

    private final LanguageElasticRepository repository;

    @Override
    public void write(@NonNull Chunk<? extends Repository> chunk) {
        write(chunk, LanguageDocument::toDocument, repository);
    }
}
