package com.hulk.processor.model;

import com.hulk.processor.repository.Repository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "language_repositories")
public class LanguageDocument {
    @Id
    private UUID id;
    private Long repositoryId;
    private String language;
    private Instant createdAt;
    private Instant updatedAt;

    public static LanguageDocument toDocument(Repository repository) {
        return new LanguageDocument(
            UUID.randomUUID(),
            repository.id(),
            repository.language(),
            repository.createdAt(),
            repository.updatedAt()
        );
    }
}
