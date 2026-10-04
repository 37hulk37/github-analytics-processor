package com.hulk.processor.configuration;

import com.hulk.processor.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.lang.NonNull;

@Configuration
@RequiredArgsConstructor
public class ElasticSearchConfiguration extends ElasticsearchConfiguration {

    private final ElasticsearchProperties properties;

    @NonNull
    @Override
    public ClientConfiguration clientConfiguration() {
        var uri = properties.getUris().stream()
            .findFirst()
            .orElseThrow(() -> new ApplicationException("uri is empty"));

        return ClientConfiguration.builder()
            .connectedTo(uri)
            .withBasicAuth(properties.getUsername(), properties.getPassword())
            .build();
    }
}
