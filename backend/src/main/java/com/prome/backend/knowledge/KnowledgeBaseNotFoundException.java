package com.prome.backend.knowledge;

import java.util.UUID;

public class KnowledgeBaseNotFoundException extends RuntimeException {
    public KnowledgeBaseNotFoundException(UUID id) {
        super("Knowledge base not found: " + id);
    }
}
