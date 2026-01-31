package com.kalli.tech.openclaw.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenController {

    @GetMapping("/token")
    public Map<String, Object> getToken(
            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OAuth2User user) {
        
        Map<String, Object> response = new HashMap<>();
        
        // Access token
        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        response.put("access_token", accessToken);
        response.put("token_type", authorizedClient.getAccessToken().getTokenType().getValue());
        response.put("expires_at", authorizedClient.getAccessToken().getExpiresAt());
        response.put("scopes", authorizedClient.getAccessToken().getScopes());
        
        // User info
        response.put("user_name", user.getName());
        response.put("user_attributes", user.getAttributes());
        
        // Client info
        response.put("client_registration_id", authorizedClient.getClientRegistration().getRegistrationId());
        response.put("principal_name", authorizedClient.getPrincipalName());
        
        return response;
    }

    @GetMapping("/user-info")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal OAuth2User user) {
        return user.getAttributes();
    }
}
