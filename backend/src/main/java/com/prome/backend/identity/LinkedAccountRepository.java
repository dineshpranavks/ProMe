package com.prome.backend.identity;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkedAccountRepository extends JpaRepository<LinkedAccount, UUID> {
    Optional<LinkedAccount> findByProviderAndProviderUserId(String provider, String providerUserId);
}
