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
@Document(indexName = "repository_commits")
public class CommitsDocument {
    @Id
    private UUID id;
    private Long repositoryId;
    private int amount;

    public static CommitsDocument toDocument(Repository repository) {
        return new CommitsDocument(
            UUID.randomUUID(),
            repository.id(),
            repository.commitsCount()
        );
    }

}
