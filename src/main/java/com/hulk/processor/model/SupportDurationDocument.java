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
@Document(indexName = "repository_support_duration")
public class SupportDurationDocument {
    @Id
    private UUID id;
    private Long repositoryId;
    private String language;

    public static SupportDurationDocument toDocument(Repository repository) {
        return new SupportDurationDocument(
            UUID.randomUUID(),
            repository.id(),
            repository.language()
        );
    }
}
