package com.hulk.processor.repository;

import java.util.UUID;

public record RepositoryInfo(
        UUID id,
        String name
) {
}
