package org.mrpaulwoods.backend.generate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.mrpaulwoods.backend.utils.Constants;

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

    public Stream<Field> stream() {
        return fields.stream();
    }

    public String getEntityClassName() {
        return entity.substring(0, 1).toUpperCase() + entity.substring(1) + Constants.ENTITY_SUFFIX;
    }

    public String getEntityObjectName() {
        return entity.substring(0, 1).toLowerCase() + entity.substring(1) + Constants.ENTITY_SUFFIX;
    }

    public String getDtoClassName() {
        return entity.substring(0, 1).toUpperCase() + entity.substring(1) + Constants.DTO_SUFFIX;
    }

    public String getMapperClassName() {
        return getEntityClassName() + Constants.MAPPER_SUFFIX;
    }

    public String getRepositoryClassName() {
        return getEntityClassName() + Constants.REPOSITORY_SUFFIX;
    }

    public String getRepositoryObjectName() {
        return getEntityObjectName() + Constants.REPOSITORY_SUFFIX;
    }

    public String getServiceClassName() {
        return getEntityClassName() + Constants.SERVICE_SUFFIX;
    }

}
