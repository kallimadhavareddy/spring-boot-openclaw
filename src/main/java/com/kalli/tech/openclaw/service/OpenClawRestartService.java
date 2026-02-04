package com.kalli.tech.openclaw.service;

import java.io.IOException;
import java.util.List;

public interface OpenClawRestartService {
    void executeCommand(List<String> command, boolean waitForCompletion) throws IOException, InterruptedException;

    void restartOpenclawWhatsAppChannel() throws IOException, InterruptedException;

    void restartOpenclawGateway() throws IOException, InterruptedException;
}