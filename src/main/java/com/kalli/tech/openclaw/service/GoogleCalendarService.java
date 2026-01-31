package com.kalli.tech.openclaw.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoogleCalendarService {

    private static final Logger logger = LoggerFactory.getLogger(GoogleCalendarService.class);

    private final OAuth2AuthorizedClientService clientService;

    public GoogleCalendarService(OAuth2AuthorizedClientService clientService) {
        this.clientService = clientService;
    }

    public List<String> upcomingEvents(OAuth2AuthenticationToken auth) {
        try {
            logger.info("Fetching calendar events for user: {}", auth.getName());
            
            OAuth2AuthorizedClient client =
                    clientService.loadAuthorizedClient(
                            auth.getAuthorizedClientRegistrationId(),
                            auth.getName()
                    );

            if (client == null) {
                logger.error("No authorized client found for user: {}", auth.getName());
                return List.of("Error: No authorized client found");
            }

            String accessToken = client.getAccessToken().getTokenValue();
            logger.info("Access token retrieved successfully");

            Calendar calendar = new Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    request -> request.getHeaders()
                            .setAuthorization("Bearer " + accessToken)
            )
                    .setApplicationName("Spring Boot Calendar App")
                    .build();

            Events events = calendar.events()
                    .list("primary")
                    .setMaxResults(10)
                    .setSingleEvents(true)
                    .setOrderBy("startTime")
                    .execute();

            List<String> result = events.getItems().stream()
                    .map(e -> e.getSummary() + " @ " +
                            (e.getStart().getDateTime() != null
                                    ? e.getStart().getDateTime()
                                    : e.getStart().getDate()))
                    .toList();

            logger.info("Successfully fetched {} calendar events", result.size());
            return result;

        } catch (Exception e) {
            logger.error("Error fetching calendar events", e);
            return List.of("Error fetching calendar events: " + e.getMessage());
        }
    }
}


