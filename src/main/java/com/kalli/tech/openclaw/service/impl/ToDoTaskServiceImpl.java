package com.kalli.tech.openclaw.service.impl;

import com.kalli.tech.openclaw.persistance.entity.ToDoTask;
import com.kalli.tech.openclaw.persistance.repository.ToDoTaskRepo;
import com.kalli.tech.openclaw.service.ToDoTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ToDoTaskServiceImpl implements ToDoTaskService {

    private final ToDoTaskRepo toDoTaskRepo;

    public ToDoTaskServiceImpl(ToDoTaskRepo toDoTaskRepo) {
        this.toDoTaskRepo = toDoTaskRepo;
    }
    
    @Override
    public ToDoTask createTask(ToDoTask toDoTask) {

        log.info("{}",toDoTask);
        // The ID should already be set by ModelMapper from TodoTaskRequest
        // If ID is null, we need to create it from the request data
        if (toDoTask.getId() == null) {
            throw new IllegalArgumentException("Task ID cannot be null. Ensure taskName and category are provided.");
        }
        return toDoTaskRepo.save(toDoTask);
    }

    @Override
    public ToDoTask updateTask(ToDoTask toDoTask) {
        return toDoTaskRepo.save(toDoTask);
    }

    @Override
    public List<ToDoTask> getTasks() {
        return toDoTaskRepo.findAll().stream()

                .collect(Collectors.groupingBy(
                        t -> t.getId().getCategory()
                ))
                .values().stream()
                .flatMap(list -> list.stream()
                        .sorted(Comparator.comparing( ToDoTask::getDueDate,
                                Comparator.nullsFirst(Comparator.naturalOrder())))
                ).toList();
    }

    @Override
    public ToDoTask getTaskById(Long id) {
        return toDoTaskRepo.findById(id).orElse(null);
    }
}
