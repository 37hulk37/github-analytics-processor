package com.hulk.processor.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.consumer")
public record RepositoryConsumerProperties(
        String topicName
) {
}
