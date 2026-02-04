package com.kalli.tech.openclaw.service.impl;

import com.kalli.tech.openclaw.service.OpenClawWhatsAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@Slf4j
public class OpenClawWhatsAppServiceImpl implements OpenClawWhatsAppService {

    /**
     * Runs an OpenClaw command on Linux and returns the output.
     *
     * @param command Full command, e.g., "openclaw agent --to +1234567890 --message 'Hello'"
     * @return Output of the command
     */
    @Override
    public String runCommand(String command) {
        StringBuilder output = new StringBuilder();
        Process process = null;

        try {
            // Use /bin/bash -c to run Linux shell commands
            log.info("Executing command: " + command);
            process = new ProcessBuilder("/bin/bash", "-c", command)
                    .redirectErrorStream(true) // merge stdout and stderr
                    .start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            output.append("Exit code: ").append(exitCode).append("\n");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            output.append("Error: ").append(e.getMessage()).append("\n");
        } finally {
            if (process != null) process.destroy();
        }

        return output.toString();
    }
}

