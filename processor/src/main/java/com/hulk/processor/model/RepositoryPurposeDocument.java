package com.hulk.processor.model;

import com.hulk.processor.ml.MlMetrics;
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
@Document(indexName = "language_purpose")
public class RepositoryPurposeDocument {
    @Id
    private UUID id;
    private String language;
    private Integer purpose;

    public static RepositoryPurposeDocument toDocument(MlMetrics metrics) {
        return new RepositoryPurposeDocument(
            UUID.randomUUID(),
            "",
            -1
        );
    }

}