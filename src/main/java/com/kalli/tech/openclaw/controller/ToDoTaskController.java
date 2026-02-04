package com.kalli.tech.openclaw.controller;

import com.kalli.tech.openclaw.model.TodoTaskRequest;
import com.kalli.tech.openclaw.persistance.entity.ToDoTask;
import com.kalli.tech.openclaw.service.ToDoTaskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.56:4200"})

@Slf4j
public class ToDoTaskController {

    private final ToDoTaskService toDoTaskService;

    private final ModelMapper modelMapper;
    
    @Autowired
    public ToDoTaskController(ToDoTaskService toDoTaskService, ModelMapper modelMapper) {
        this.toDoTaskService = toDoTaskService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/todo/create")
    ResponseEntity<ToDoTask> createTask(@RequestBody @Valid TodoTaskRequest todoTaskRequest) {
        // Create ToDoTask entity
        return ResponseEntity.status(HttpStatus.CREATED).body(toDoTaskService.createTask(modelMapper.map(todoTaskRequest, ToDoTask.class)));
    }

    @GetMapping("/todo/tasks")
    ResponseEntity<List<ToDoTask>> getTasks() {
        return ResponseEntity.ok(toDoTaskService.getTasks());
    }

    @PostMapping("/todo/update")
    ResponseEntity<ToDoTask> updateTask(@RequestBody TodoTaskRequest todoTaskRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDoTaskService.createTask(modelMapper.map(todoTaskRequest, ToDoTask.class)));
    }

}
