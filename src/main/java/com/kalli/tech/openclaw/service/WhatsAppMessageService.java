package com.kalli.tech.openclaw.service;

import com.kalli.tech.openclaw.model.Category;
import com.kalli.tech.openclaw.persistance.entity.ToDoTask;
import org.springframework.scheduling.config.Task;

import java.util.List;
import java.util.Map;

public interface WhatsAppMessageService {
    public String prepareOfficeMessageList(List<ToDoTask> tasks, Category category);

    public String prepareOpenCladCommand(String message, Category category,String groupId) ;

    public default Map<Category,String> getWhatsAppGroups() {
        return Map.of(
                Category.WORK, "120363421568208294@g.us",
                Category.HOME, "120363278813740302@g.us",
                Category.SCHOOL, "120363404739904487@g.us",
                Category.OTHER, "120363367217422068@g.us"
        );
    }
}
