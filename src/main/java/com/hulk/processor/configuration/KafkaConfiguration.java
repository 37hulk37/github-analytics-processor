package com.hulk.processor.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final KafkaProperties kafkaProperties;

    @Bean
    public Properties repositoryCunsumerGroupProperties() {
        var properties = new Properties();
        properties.putAll(kafkaProperties.buildConsumerProperties());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "repository-step-group");

        return properties;
    }

    @Bean
    public Properties mlConsumerGroupProperties() {
        var properties = new Properties();
        properties.putAll(kafkaProperties.buildConsumerProperties());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "ml-step-group");

        return properties;
    }


}
