package org.mrpaulwoods.backend.technology;

import jakarta.validation.Valid;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;

public interface Technology {
    void addImports(AppRequest appRequest, StringBuilder sb);

    default void addClassAnnotations(@Valid AppRequest appRequest, StringBuilder sb) {
    }

    default void addFieldAnnotation(@Valid AppRequest appRequest, Field field, StringBuilder sb) {

    }
}
