package com.prome.backend.identity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class UserPrincipal extends AbstractAuthenticationToken {
    private final User user;

    public UserPrincipal(User user) {
        super(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.user = user;
        setAuthenticated(true);
    }

    public User user() { return user; }
    public UUID userId() { return user.getId(); }
    @Override public Object getCredentials() { return ""; }
    @Override public Object getPrincipal() { return this; }
}
