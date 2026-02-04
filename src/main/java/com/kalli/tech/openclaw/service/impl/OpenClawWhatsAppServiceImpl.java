package com.kalli.tech.openclaw.service.impl;

import com.kalli.tech.openclaw.service.OpenClawWhatsAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OpenClawWhatsAppServiceImpl implements OpenClawWhatsAppService {

    @Override
    public String runCommand(List<String> command) {
        StringBuilder output = new StringBuilder();

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        try {
            log.info("Executing: {}", String.join(" ", command));
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            boolean finished = process.waitFor(60, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                return output.append("Error: timeout\n").toString();
            }

            output.append("Exit code: ").append(process.exitValue()).append("\n");
            return output.toString();

        } catch (Exception e) {
            log.error("Command failed", e);
            return output.append("Error: ").append(e.getMessage()).append("\n").toString();
        }
    }

}

