package com.kalli.tech.openclaw.model;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Data
    public class TodoTaskIdRequest {
    @NotBlank
    @NotNull
    private String taskName;
    @NotBlank
    @NotNull
    private Category category;

}
