package com.kalli.tech.openclaw.persistance.entity;

import com.kalli.tech.openclaw.model.Category;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TodoTaskId implements Serializable {

    private String taskName;

    @Enumerated(EnumType.STRING)
    private Category category;

    public TodoTaskId() {}

    public TodoTaskId(String taskName, Category category) {
        this.taskName = taskName;
        this.category = category;
    }

    // getters & setters
    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    // equals & hashCode are REQUIRED for composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TodoTaskId)) return false;
        TodoTaskId that = (TodoTaskId) o;
        return Objects.equals(taskName, that.taskName) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, category);
    }
}


