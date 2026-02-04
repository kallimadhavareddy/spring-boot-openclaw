package com.kalli.tech.openclaw.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.time.LocalDate;


@Data
public class TodoTaskRequest {
   private TodoTaskIdRequest id;
    private String description;
    private Status status;
    private Priority priority;
    @NotNull
    private LocalDate dueDate;
}
