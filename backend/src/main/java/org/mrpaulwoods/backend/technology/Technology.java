package org.mrpaulwoods.backend.technology;

import jakarta.validation.Valid;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;

public interface Technology {

    default void importCodeBlock(AppRequest appRequest, StringBuilder sb) {
    }

    default void classAnnotationsCodeBlock(@Valid AppRequest appRequest, StringBuilder sb) {
    }

    default void fieldAnnotationCodeBlock(@Valid AppRequest appRequest, Field field, StringBuilder sb) {
    }

}
