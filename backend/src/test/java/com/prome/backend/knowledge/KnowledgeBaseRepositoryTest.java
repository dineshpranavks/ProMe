package com.prome.backend.knowledge;

import static org.assertj.core.api.Assertions.assertThat;

import com.prome.backend.identity.User;
import com.prome.backend.identity.UserRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class KnowledgeBaseRepositoryTest {
    @Autowired
    private KnowledgeBaseRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void ownerScopedSearchDoesNotReturnAnotherUsersKnowledgeBase() {
        User owner = userRepository.save(new User("owner@example.com", "Owner", null));
        User other = userRepository.save(new User("other@example.com", "Other", null));
        repository.save(new KnowledgeBase(owner, "Owner notes", "Private"));
        repository.save(new KnowledgeBase(other, "Owner notes", "Private"));

        assertThat(repository.findByUserIdAndNameContainingIgnoreCase(
                owner.getId(), "owner", PageRequest.of(0, 10)).getContent())
                .hasSize(1)
                .allMatch(item -> item.getUser().getId().equals(owner.getId()));
    }
}
