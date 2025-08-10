package org.mrpaulwoods.backend.generate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AppRequest {

    @NotBlank
    @Size(min = 2, message = "Entity must be at least 2 characters long")
    private String entity;

    @NotBlank
    @Size(min = 2, message = "Pkg must be at least 2 characters long")
    private String pkg;

    @NotBlank
    @Size(min = 2, message = "Fields must be at least 2 characters long")
    private String fields;

    public String getClassName() {
        return entity.substring(0, 1).toUpperCase() + entity.substring(1);
    }

}
