package com.hulk.processor.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public record TopicProperties(
    String topic
) {
}
