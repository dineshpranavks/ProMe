package com.prome.backend.identity;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final LinkedAccountRepository linkedAccountRepository;

    public UserService(UserRepository userRepository, LinkedAccountRepository linkedAccountRepository) {
        this.userRepository = userRepository;
        this.linkedAccountRepository = linkedAccountRepository;
    }

    @Transactional
    public User authenticateOrRegister(String provider, String providerUserId, String email,
                                       String displayName, String profileImageUrl) {
        Optional<LinkedAccount> linked = linkedAccountRepository
                .findByProviderAndProviderUserId(provider, providerUserId);
        if (linked.isPresent()) {
            User user = linked.get().getUser();
            user.updateProfile(email, displayName, profileImageUrl);
            return user;
        }
        User user = userRepository.save(new User(email, displayName, profileImageUrl));
        linkedAccountRepository.save(new LinkedAccount(user, provider, providerUserId));
        return user;
    }

    @Transactional
    public User linkAccount(User user, String provider, String providerUserId, String email,
                            String displayName, String profileImageUrl) {
        Optional<LinkedAccount> existing = linkedAccountRepository
                .findByProviderAndProviderUserId(provider, providerUserId);
        if (existing.isPresent() && !existing.get().getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("This provider account is already linked to another user");
        }
        if (existing.isEmpty()) {
            linkedAccountRepository.save(new LinkedAccount(user, provider, providerUserId));
        }
        user.updateProfile(email, displayName, profileImageUrl);
        return user;
    }
}
