package com.prome.backend.identity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final IdentityAuthenticationService identityService;

    public AuthController(IdentityAuthenticationService identityService) {
        this.identityService = identityService;
    }

    @GetMapping("/api/auth/me")
    public UserResponse currentUser(@AuthenticationPrincipal UserPrincipal principal) {
        return UserResponse.from(principal.user());
    }

    @GetMapping("/api/auth/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

    @GetMapping("/api/auth/link/{provider}")
    public void link(@PathVariable String provider, HttpServletRequest request,
                     HttpServletResponse response) throws IOException {
        if (!provider.equals("google") && !provider.equals("github")) {
            throw new IllegalArgumentException("Unsupported OAuth provider");
        }
        identityService.rememberLinkTarget(request,
                (UserPrincipal) request.getUserPrincipal(), provider);
        response.sendRedirect("/oauth2/authorization/" + provider);
    }
}
