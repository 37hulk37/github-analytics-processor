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
@Document(indexName = "language_popularity_forecast")
public class LanguagePopularityForecastDocument {
    @Id
    private UUID id;
    private String language;
    private Integer popularity;

    public static LanguagePopularityForecastDocument toDocument(MlMetrics metrics) {
        return new LanguagePopularityForecastDocument(
            UUID.randomUUID(),
            "",
            -1
        );
    }
}
