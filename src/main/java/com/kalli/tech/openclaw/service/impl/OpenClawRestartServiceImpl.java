package com.kalli.tech.openclaw.service.impl;

import com.kalli.tech.openclaw.service.OpenClawRestartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
@Slf4j
public class OpenClawRestartServiceImpl implements OpenClawRestartService {
    /**
     * Executes a Linux command and optionally waits for it.
     */
    public void executeCommand(List<String> command, boolean waitForCompletion) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true); // merge stdout + stderr

        Process process = pb.start();

        // Log output
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        if (waitForCompletion) {
            int exitCode = process.waitFor();
            System.out.println("Command exited with code: " + exitCode);
        }
    }

    /**
     * Convenience method for restarting openclaw gateway
     */

    public void restartOpenclawWhatsAppChannel() throws IOException, InterruptedException {
        executeCommand(
                List.of("openclaw", "channels", "login", "--channel", "whatsapp", "--account", "default"),
                true
        );

        sleepForAMinute();
        log.info("Restart OpenClaw Executed successfully");
    }

    public void restartOpenclawGateway() throws IOException, InterruptedException {
        executeCommand(
                List.of("bash", "-c", "nohup openclaw gateway restart > openclaw.log 2>&1 &"),
                false
        );
        sleepForAMinute();
        log.info("Re login whats app Executed successfully");
    }

    public void sleepForAMinute(){
        try {
            // 1 minute = 60,000 milliseconds
            Thread.sleep(60_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupt
            e.printStackTrace();
        }

    }

}

