package com.kalli.tech.openclaw.service;

import com.kalli.tech.openclaw.persistance.entity.ToDoTask;
import com.kalli.tech.openclaw.persistance.entity.TodoTaskId;

import java.util.List;

public interface ToDoTaskService {
    public ToDoTask createTask(ToDoTask toDoTask);

    public ToDoTask updateTask(ToDoTask toDoTask);

    public List<ToDoTask> getTasks();

    public ToDoTask getTaskById(TodoTaskId id);
}
