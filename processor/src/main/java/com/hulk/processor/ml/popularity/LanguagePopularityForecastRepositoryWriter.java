package com.hulk.processor.ml.popularity;

import com.hulk.processor.ml.MlMetrics;
import com.hulk.processor.model.LanguagePopularityForecastDocument;
import com.hulk.processor.repository.AbstractCompletableFutureItemWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LanguagePopularityForecastRepositoryWriter extends
    AbstractCompletableFutureItemWriter<MlMetrics> {

    private final LanguagePopularityForecastElasticRepository repository;

    @Override
    public void write(@NonNull Chunk<? extends MlMetrics> chunk) {
        write(chunk, LanguagePopularityForecastDocument::toDocument, repository);
    }

}
