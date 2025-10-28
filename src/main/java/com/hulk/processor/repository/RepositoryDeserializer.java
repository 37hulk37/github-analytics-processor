package com.hulk.processor.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RepositoryDeserializer implements Deserializer<Repository> {

    private final ObjectMapper objectMapper;

    @Override
    public Repository deserialize(String topic, byte[] data) {
        return null;
    }
}
