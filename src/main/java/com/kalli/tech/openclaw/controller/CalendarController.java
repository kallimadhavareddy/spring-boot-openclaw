package com.kalli.tech.openclaw.controller;

import com.kalli.tech.openclaw.service.GoogleCalendarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@Slf4j
public class CalendarController {

    private final GoogleCalendarService service;

    public CalendarController(GoogleCalendarService service) {
        this.service = service;
    }

    @GetMapping("/events")
    public List<String> events(OAuth2AuthenticationToken auth) {

        try {
            return service.upcomingEvents(auth);
        } catch (Exception e) {
            return List.of("Error: " + e.getMessage());
        }
    }
}

