package com.kalli.tech.openclaw.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DebugController {

    @GetMapping("/debug-auth")
    public Map<String, Object> debugAuth(@AuthenticationPrincipal OAuth2User user) {
        Map<String, Object> debug = new HashMap<>();
        
        // Check authentication status
        var auth = SecurityContextHolder.getContext().getAuthentication();
        debug.put("authentication_class", auth != null ? auth.getClass().getSimpleName() : "null");
        debug.put("is_authenticated", auth != null && auth.isAuthenticated());
        debug.put("principal_class", auth != null && auth.getPrincipal() != null ? auth.getPrincipal().getClass().getSimpleName() : "null");
        
        // OAuth2 specific info
        if (auth instanceof OAuth2AuthenticationToken oauthToken) {
            debug.put("authorized_client_registration_id", oauthToken.getAuthorizedClientRegistrationId());
            debug.put("principal_name", oauthToken.getName());
            debug.put("authorities", oauthToken.getAuthorities());
        }
        
        // User info
        if (user != null) {
            debug.put("user_attributes", user.getAttributes());
            debug.put("user_name", user.getName());
        } else {
            debug.put("user", "null");
        }
        
        return debug;
    }

    @GetMapping("/simple-token")
    public Map<String, Object> simpleToken(@AuthenticationPrincipal OAuth2User user) {
        Map<String, Object> result = new HashMap<>();
        
        if (user != null) {
            result.put("status", "authenticated");
            result.put("user_name", user.getName());
            result.put("email", user.getAttribute("email"));
            result.put("has_token", true);
            
            // Try to get token from authentication
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth instanceof OAuth2AuthenticationToken oauthToken) {
                result.put("client_registration_id", oauthToken.getAuthorizedClientRegistrationId());
            }
        } else {
            result.put("status", "not_authenticated");
            result.put("message", "Please authenticate first");
        }
        
        return result;
    }
}
