package com.kalli.tech.openclaw.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TodoTaskIdRequest {
    @NotBlank
    @NotNull
    private String taskName;
    @NotBlank
    @NotNull
    private Category category;

}
