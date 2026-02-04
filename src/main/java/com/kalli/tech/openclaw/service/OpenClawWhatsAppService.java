package com.kalli.tech.openclaw.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpenClawWhatsAppService {
    public String runCommand(List<String> command);
}

