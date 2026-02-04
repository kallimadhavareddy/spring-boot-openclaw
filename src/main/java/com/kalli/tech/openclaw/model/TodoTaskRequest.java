package com.kalli.tech.openclaw.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
public class TodoTaskRequest {
    private TodoTaskIdRequest id;
    private String description;
    private Status status;
    private Priority priority;
    @NotNull
    private LocalDate dueDate;
    @NotNull
    private LocalTime dueTime;
}
