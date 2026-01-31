package com.kalli.tech.openclaw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test-oauth")
    public String testOAuth() {
        return "OAuth endpoint is working! Try: <a href='/oauth2/authorization/google'>Login with Google</a>";
    }

    @GetMapping("/health")
    public String health() {
        return "Application is running!";
    }
}
