package com.prome.backend.knowledge;

import com.prome.backend.identity.UserPrincipal;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/knowledge-bases")
public class KnowledgeBaseController {
    private final KnowledgeBaseService service;
    private final KnowledgeBaseMapper mapper;

    public KnowledgeBaseController(KnowledgeBaseService service, KnowledgeBaseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<KnowledgeBaseResponse> create(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody CreateKnowledgeBaseRequest request,
            UriComponentsBuilder uriBuilder) {
        KnowledgeBaseResponse response = mapper.toResponse(service.create(principal.userId(), request));
        URI location = uriBuilder.path("/api/knowledge-bases/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public Page<KnowledgeBaseResponse> list(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        return service.list(principal.userId(), search, pageable).map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    public KnowledgeBaseResponse get(@AuthenticationPrincipal UserPrincipal principal, @PathVariable UUID id) {
        return mapper.toResponse(service.get(principal.userId(), id));
    }

    @PutMapping("/{id}")
    public KnowledgeBaseResponse update(@AuthenticationPrincipal UserPrincipal principal,
                                        @PathVariable UUID id,
                                        @Valid @RequestBody UpdateKnowledgeBaseRequest request) {
        return mapper.toResponse(service.update(principal.userId(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserPrincipal principal, @PathVariable UUID id) {
        service.delete(principal.userId(), id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/archive")
    public KnowledgeBaseResponse archive(@AuthenticationPrincipal UserPrincipal principal, @PathVariable UUID id) {
        return mapper.toResponse(service.archive(principal.userId(), id));
    }
}
