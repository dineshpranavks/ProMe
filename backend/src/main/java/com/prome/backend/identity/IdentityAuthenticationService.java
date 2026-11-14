package com.prome.backend.identity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class IdentityAuthenticationService implements AuthenticationSuccessHandler {
    private static final String LINK_USER_ID = "OAUTH_LINK_USER_ID";
    private static final String LINK_PROVIDER = "OAUTH_LINK_PROVIDER";
    private final UserService userService;
    private final UserRepository userRepository;
    private final SecurityContextRepository securityContextRepository;
    private final String frontendUrl;

    public IdentityAuthenticationService(UserService userService, UserRepository userRepository,
                                         SecurityContextRepository securityContextRepository,
                                         @Value("${app.frontend-url}") String frontendUrl) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.securityContextRepository = securityContextRepository;
        this.frontendUrl = frontendUrl;
    }

    public void rememberLinkTarget(HttpServletRequest request, UserPrincipal principal, String provider) {
        HttpSession session = request.getSession(true);
        session.setAttribute(LINK_USER_ID, principal.userId().toString());
        session.setAttribute(LINK_PROVIDER, provider);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (!(authentication instanceof OAuth2AuthenticationToken oauth)) {
            response.sendRedirect(frontendUrl);
            return;
        }
        String provider = oauth.getAuthorizedClientRegistrationId();
        OAuth2User oauthUser = oauth.getPrincipal();
        ProviderIdentity identity = ProviderIdentity.from(provider, oauthUser);
        HttpSession session = request.getSession(false);
        String linkUserId = session == null ? null : (String) session.getAttribute(LINK_USER_ID);
        String linkProvider = session == null ? null : (String) session.getAttribute(LINK_PROVIDER);
        User user;
        if (linkUserId != null && provider.equals(linkProvider)) {
            user = userRepository.findById(java.util.UUID.fromString(linkUserId))
                    .orElseThrow(() -> new IllegalStateException("Link target user no longer exists"));
            userService.linkAccount(user, provider, identity.providerUserId(), identity.email(),
                    identity.displayName(), identity.profileImageUrl());
            session.removeAttribute(LINK_USER_ID);
            session.removeAttribute(LINK_PROVIDER);
        } else {
            user = userService.authenticateOrRegister(provider, identity.providerUserId(), identity.email(),
                    identity.displayName(), identity.profileImageUrl());
        }

        UserPrincipal principal = new UserPrincipal(user);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(principal);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        response.sendRedirect(frontendUrl);
    }

    private record ProviderIdentity(String providerUserId, String email, String displayName,
                                    String profileImageUrl) {
        static ProviderIdentity from(String provider, OAuth2User user) {
            if ("google".equals(provider)) {
                return new ProviderIdentity(user.getAttribute("sub"), user.getAttribute("email"),
                        firstNonBlank(user.getAttribute("name"), user.getAttribute("email")),
                        user.getAttribute("picture"));
            }
            return new ProviderIdentity(String.valueOf(user.getAttribute("id")), user.getAttribute("email"),
                    firstNonBlank(user.getAttribute("name"), user.getAttribute("login"), "GitHub user"),
                    user.getAttribute("avatar_url"));
        }

        private static String firstNonBlank(String... values) {
            for (String value : values) if (value != null && !value.isBlank()) return value;
            return "User";
        }
    }
}
