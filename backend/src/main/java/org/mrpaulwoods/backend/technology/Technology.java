package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;

public sealed interface Technology permits LombokTechnology, SpringDataTechnology, UUIDTechnology, ValidationTechnology {

    default void importCodeBlock(AppRequest appRequest, StringBuilder sb) {
    }

    default void classAnnotationsCodeBlock(AppRequest appRequest, StringBuilder sb) {
    }

    default void fieldAnnotationCodeBlock(AppRequest appRequest, Field field, StringBuilder sb) {
    }

}
