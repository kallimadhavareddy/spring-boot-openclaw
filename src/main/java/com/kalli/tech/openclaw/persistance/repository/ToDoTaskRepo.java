package com.kalli.tech.openclaw.persistance.repository;

import com.kalli.tech.openclaw.persistance.entity.ToDoTask;
import com.kalli.tech.openclaw.persistance.entity.TodoTaskId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoTaskRepo extends JpaRepository<ToDoTask, TodoTaskId> {
}
