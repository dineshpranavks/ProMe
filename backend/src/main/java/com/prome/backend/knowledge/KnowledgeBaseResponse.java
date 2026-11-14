package com.prome.backend.knowledge;

import java.time.Instant;
import java.util.UUID;

public record KnowledgeBaseResponse(
        UUID id,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt,
        boolean archived) {
}
