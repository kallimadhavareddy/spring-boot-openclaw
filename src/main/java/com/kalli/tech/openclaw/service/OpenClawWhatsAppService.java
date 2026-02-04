package com.kalli.tech.openclaw.service;

import org.springframework.stereotype.Service;

@Service
public interface OpenClawWhatsAppService {

    /**
     * Runs an OpenClaw command on Linux and returns the output.
     *
     * @param command Full command, e.g., "openclaw agent --to +1234567890 --message 'Hello'"
     * @return Output of the command
     */
    public String runCommand(String command);
}

