package com.prome.backend.knowledge;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeBaseRepository extends JpaRepository<KnowledgeBase, UUID> {
    Page<KnowledgeBase> findByUserId(UUID userId, Pageable pageable);
    Page<KnowledgeBase> findByUserIdAndNameContainingIgnoreCase(
            UUID userId, String name, Pageable pageable);
    java.util.Optional<KnowledgeBase> findByIdAndUserId(UUID id, UUID userId);
}
