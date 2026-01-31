package com.kalli.tech.openclaw.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring SecurityFilterChain");
        
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login-page", "/debug", "/oauth-test", "/test-oauth", "/health", "/debug-auth", "/simple-token", "/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> {
                    logger.info("Configuring OAuth2 login");
                    oauth2
                            .loginPage("/login-page")
                            .defaultSuccessUrl("/", true)
                            .failureUrl("/login-page?error=true");
                });

        logger.info("SecurityFilterChain configured successfully");
        return http.build();
    }
}

