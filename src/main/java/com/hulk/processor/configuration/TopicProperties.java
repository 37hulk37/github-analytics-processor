package com.hulk.processor.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.kafka.consumer")
public record TopicProperties(
    String topic
) {
}
