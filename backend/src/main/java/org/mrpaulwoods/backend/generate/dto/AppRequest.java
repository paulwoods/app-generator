package org.mrpaulwoods.backend.generate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.nio.file.FileSystems;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppRequest {

    @NotBlank
    @Size(min = 2, message = "Entity must be at least 2 characters long")
    private String entity;

    @NotBlank
    @Size(min = 2, message = "Pkg must be at least 2 characters long")
    private String pkg;

    @NotNull
    @Singular
    private List<Field> fields;

    public String getClassName() {
        return entity.substring(0, 1).toUpperCase() + entity.substring(1);
    }

    public String getPkgAsFolder() {
        String separator = FileSystems.getDefault().getSeparator();
        return pkg.replaceAll("\\.", separator);
    }

}
