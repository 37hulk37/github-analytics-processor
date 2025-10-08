package com.hulk.processor.repository;

import java.util.UUID;

public record RepositoryResult(
    UUID id,
    String name,
    String fileLink
) {
}
