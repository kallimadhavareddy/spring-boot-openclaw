package com.kalli.tech.openclaw.persistance.entity;

import com.kalli.tech.openclaw.model.Priority;
import com.kalli.tech.openclaw.model.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@Data
public class ToDoTask {
    @EmbeddedId
    private TodoTaskId id;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private LocalDate dueDate;
}
