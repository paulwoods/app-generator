package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.export.FileBuilderType;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;

public sealed interface Technology permits LombokTechnology, SpringDataTechnology, UUIDTechnology, ValidationTechnology {

    default void importCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {
    }

    default void classAnnotationsCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {
    }

    default void fieldAnnotationCodeBlock(AppRequest appRequest, FileBuilderType type, Field field, Code code) {
    }

}
