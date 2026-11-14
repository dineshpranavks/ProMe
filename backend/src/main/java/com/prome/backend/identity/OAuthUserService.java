package com.prome.backend.identity;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuthUserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        String provider = request.getClientRegistration().getRegistrationId();
        if (!provider.equals("google") && !provider.equals("github")) {
            throw new OAuth2AuthenticationException(new OAuth2Error("unsupported_provider"),
                    "Unsupported OAuth provider: " + provider);
        }
        return super.loadUser(request);
    }
}
