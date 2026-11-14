package com.prome.backend.knowledge;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prome.backend.identity.User;
import com.prome.backend.identity.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KnowledgeBaseServiceTest {
    @Mock
    private KnowledgeBaseRepository repository;
    @Mock
    private UserRepository userRepository;

    private KnowledgeBaseService service;
    private UUID userId;

    @BeforeEach
    void setUp() {
        service = new KnowledgeBaseService(repository, userRepository);
        userId = UUID.randomUUID();
    }

    @Test
    void getUsesOwnerScopedLookupAndDoesNotExposeAnotherUsersResource() {
        UUID knowledgeBaseId = UUID.randomUUID();
        when(repository.findByIdAndUserId(knowledgeBaseId, userId)).thenReturn(Optional.empty());

        assertThrows(KnowledgeBaseNotFoundException.class, () -> service.get(userId, knowledgeBaseId));

        verify(repository).findByIdAndUserId(knowledgeBaseId, userId);
        verify(repository, never()).findById(knowledgeBaseId);
    }

    @Test
    void createUsesAuthenticatedUserFromRepository() {
        User user = new User("user@example.com", "User", null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(repository.save(any(KnowledgeBase.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.create(userId, new CreateKnowledgeBaseRequest("  Personal  ", "  Notes  "));

        verify(userRepository).findById(userId);
        verify(repository).save(any(KnowledgeBase.class));
    }
}
