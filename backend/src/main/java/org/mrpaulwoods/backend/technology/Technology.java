package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;
import org.mrpaulwoods.backend.types.FileBuilderType;

public sealed interface Technology permits LocalDateTimeTechnology, LombokTechnology, SpringDataTechnology, UUIDTechnology, ValidationTechnology {

    default void importCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {
    }

    default void classAnnotationsCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {
    }

    default void fieldAnnotationCodeBlock(AppRequest appRequest, FileBuilderType type, Field field, Code code) {
    }

}
