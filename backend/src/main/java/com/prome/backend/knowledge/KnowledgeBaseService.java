package com.prome.backend.knowledge;

import com.prome.backend.identity.User;
import com.prome.backend.identity.UserRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KnowledgeBaseService {
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 50;

    private final KnowledgeBaseRepository repository;
    private final UserRepository userRepository;

    public KnowledgeBaseService(KnowledgeBaseRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public KnowledgeBase create(UUID userId, CreateKnowledgeBaseRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Authenticated user no longer exists"));
        return repository.save(new KnowledgeBase(user, request.name().trim(), normalizeDescription(request.description())));
    }

    @Transactional(readOnly = true)
    public Page<KnowledgeBase> list(UUID userId, String search, Pageable pageable) {
        Pageable safePageable = safePageable(pageable);
        if (search == null || search.isBlank()) {
            return repository.findByUserId(userId, safePageable);
        }
        return repository.findByUserIdAndNameContainingIgnoreCase(userId, search.trim(), safePageable);
    }

    @Transactional(readOnly = true)
    public KnowledgeBase get(UUID userId, UUID id) {
        return repository.findByIdAndUserId(id, userId).orElseThrow(() -> new KnowledgeBaseNotFoundException(id));
    }

    @Transactional
    public KnowledgeBase update(UUID userId, UUID id, UpdateKnowledgeBaseRequest request) {
        KnowledgeBase knowledgeBase = get(userId, id);
        knowledgeBase.update(request.name().trim(), normalizeDescription(request.description()));
        return knowledgeBase;
    }

    @Transactional
    public void delete(UUID userId, UUID id) {
        KnowledgeBase knowledgeBase = get(userId, id);
        repository.delete(knowledgeBase);
    }

    @Transactional
    public KnowledgeBase archive(UUID userId, UUID id) {
        KnowledgeBase knowledgeBase = get(userId, id);
        knowledgeBase.archive();
        return knowledgeBase;
    }

    private Pageable safePageable(Pageable pageable) {
        int size = pageable == null ? DEFAULT_PAGE_SIZE : Math.min(pageable.getPageSize(), MAX_PAGE_SIZE);
        int page = pageable == null ? 0 : pageable.getPageNumber();
        Sort sort = pageable == null ? Sort.by(Sort.Direction.DESC, "createdAt") : pageable.getSort();
        return PageRequest.of(Math.max(page, 0), size, sort.isSorted() ? sort : Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    private String normalizeDescription(String description) {
        return description == null ? null : description.trim();
    }
}
