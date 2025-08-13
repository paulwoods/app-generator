package org.mrpaulwoods.backend.generate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.mrpaulwoods.backend.utils.Constants;

import java.nio.file.FileSystems;
import java.util.List;
import java.util.stream.Stream;

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

    public String getPkgAsFolder() {
        String separator = FileSystems.getDefault().getSeparator();
        return pkg.replaceAll("\\.", separator);
    }

    public String getEntityClassName() {
        return entity.substring(0, 1).toUpperCase() + entity.substring(1) + Constants.ENTITY_SUFFIX;
    }

    public String getDtoClassName() {
        return entity.substring(0, 1).toUpperCase() + entity.substring(1) + Constants.DTO_SUFFIX;
    }

    public Stream<Field> stream() {
        return fields.stream();
    }

}
