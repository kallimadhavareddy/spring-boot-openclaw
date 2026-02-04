package com.kalli.tech.openclaw.service.impl;

import com.kalli.tech.openclaw.model.Category;
import com.kalli.tech.openclaw.model.Status;
import com.kalli.tech.openclaw.persistance.entity.ToDoTask;
import com.kalli.tech.openclaw.service.WhatsAppMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WhatsAppMessageServiceImpl implements WhatsAppMessageService {
    @Override
    public String prepareOpenCladCommand(String message, Category category,String groupId) {

        if(message!=null || !message.isEmpty()){
        String command = "openclaw message send --target " + groupId + " --message \"" + message + "\"";
        log.info("openClaw command {}",command);
        return command;
        }
        return "";
    }




    @Override
    public String prepareOfficeMessageList(List<ToDoTask> tasks, Category category) {
        final DateTimeFormatter DD_MM_YY = DateTimeFormatter.ofPattern("dd-MM-yy");
        String header =
                "* Task List *" +
                        "\n" +
                        String.format(
                                "%-12s | %-8s | %-8s | %s%n",
                                "DATE", "PRIORITY", "CATEGORY", "TASK"
                        ) +
                        "-------------+----------+----------+----------------------\n";

        String whatsAppMessageBody = tasks.stream().filter(toDoTask -> !toDoTask.getStatus().equals(Status.COMPLETED)).
                filter(todoTask -> todoTask.getId().getCategory().equals(category)).
                map(toDoTask -> String.format(
                        "%-12s | %-8s | %-8s | %s",
                        toDoTask.getDueDate().format(DD_MM_YY),
                        toDoTask.getPriority(),
                        toDoTask.getId().getCategory(),
                        toDoTask.getId().getTaskName())).collect(Collectors.joining("\n"));

        log.info("jus to print{}",whatsAppMessageBody.length());
        return whatsAppMessageBody.isEmpty() ? "" : (header + whatsAppMessageBody);
    }

}
