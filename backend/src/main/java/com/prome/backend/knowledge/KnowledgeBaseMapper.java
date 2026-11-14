package com.prome.backend.knowledge;

import org.springframework.stereotype.Component;

@Component
public class KnowledgeBaseMapper {
    public KnowledgeBaseResponse toResponse(KnowledgeBase knowledgeBase) {
        return new KnowledgeBaseResponse(
                knowledgeBase.getId(),
                knowledgeBase.getName(),
                knowledgeBase.getDescription(),
                knowledgeBase.getCreatedAt(),
                knowledgeBase.getUpdatedAt(),
                knowledgeBase.isArchived());
    }
}
