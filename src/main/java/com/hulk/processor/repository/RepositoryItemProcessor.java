package com.hulk.processor.repository;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class RepositoryItemProcessor implements ItemProcessor<RepositoryInfo, RepositoryResult> {

    @Override
    public RepositoryResult process(@NonNull RepositoryInfo item) {
        return null;
    }

}
