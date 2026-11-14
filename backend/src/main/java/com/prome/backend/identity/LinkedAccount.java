package com.prome.backend.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "linked_accounts", uniqueConstraints = @UniqueConstraint(
        name = "uq_linked_accounts_provider_identity", columnNames = {"provider", "provider_user_id"}))
public class LinkedAccount {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false) private User user;
    @Column(nullable = false, length = 32) private String provider;
    @Column(name = "provider_user_id", nullable = false) private String providerUserId;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;

    protected LinkedAccount() {}
    public LinkedAccount(User user, String provider, String providerUserId) {
        this.user = user;
        this.provider = provider;
        this.providerUserId = providerUserId;
    }
    public User getUser() { return user; }
    public String getProvider() { return provider; }
    public String getProviderUserId() { return providerUserId; }
    @jakarta.persistence.PrePersist
    void onCreate() { createdAt = Instant.now(); }
}
