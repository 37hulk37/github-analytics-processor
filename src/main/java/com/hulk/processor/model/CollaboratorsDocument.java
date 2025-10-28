package com.hulk.processor.model;

import com.hulk.processor.repository.Repository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "language_repositories")
public class CollaboratorsDocument {
    @Id
    private UUID id;
    private Long repositoryId;
    private int amount;

    //todo: amount
    public static CollaboratorsDocument toDocument(Repository repository) {
        return new CollaboratorsDocument(
            UUID.randomUUID(),
            repository.id(),
            0
        );
    }
}
