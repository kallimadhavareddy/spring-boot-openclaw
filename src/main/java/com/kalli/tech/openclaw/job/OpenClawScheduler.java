package com.kalli.tech.openclaw.job;


import com.kalli.tech.openclaw.persistance.entity.ToDoTask;
import com.kalli.tech.openclaw.service.OpenClawRestartService;
import com.kalli.tech.openclaw.service.OpenClawWhatsAppService;
import com.kalli.tech.openclaw.service.ToDoTaskService;
import com.kalli.tech.openclaw.service.WhatsAppMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class OpenClawScheduler {

    public final OpenClawWhatsAppService openClawService;
    private final ToDoTaskService toDoTaskService;
    private final OpenClawRestartService openClawRestartService;

    private final WhatsAppMessageService whatsAppMessageService;

    @Autowired
    public OpenClawScheduler(OpenClawWhatsAppService openClawService, ToDoTaskService toDoTaskService, OpenClawRestartService openClawRestartService, WhatsAppMessageService whatsAppMessageService) {
        this.openClawService = openClawService;
        this.toDoTaskService = toDoTaskService;
        this.openClawRestartService = openClawRestartService;
        this.whatsAppMessageService = whatsAppMessageService;
    }

    @Scheduled(cron = "0 0 6-23/2 * * *")
    public void toDoTaskScheduler() throws IOException, InterruptedException {
        //restart
        try {
            try {
                openClawRestartService.restartOpenclawGateway();
                //re login
                openClawRestartService.restartOpenclawWhatsAppChannel();
            } catch (Exception e) {
                log.error("Error while restarting OpenClaw Gateway", e);
            }

            List<ToDoTask> tasks = toDoTaskService.getTasks().stream()
                    .sorted(Comparator.comparing(
                            ToDoTask::getDueDate,
                            Comparator.nullsLast(Comparator.naturalOrder())
                    ))
                    .collect(Collectors.toList());


            whatsAppMessageService.getWhatsAppGroups().forEach((category, groupId) -> {

                List<String> args = whatsAppMessageService.prepareOpenClawSendArgs(
                        whatsAppMessageService.prepareOfficeMessageList(tasks, category),
                        groupId
                );
                if (!args.isEmpty()) {
                    String status = openClawService.runCommand(args);
                    log.info("Command Execution Status: {}", status);
                }
            });


        } catch (Exception ex) {
            log.error("Error in job Scheduler {}", ex.getMessage());
        }
    }
}

