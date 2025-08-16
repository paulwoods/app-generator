package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;
import org.mrpaulwoods.backend.types.FileBuilderType;
import org.springframework.stereotype.Component;

@Component
public final class ValidationTechnology implements Technology {

    @Override
    public void importCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {

        switch (type) {
            case DTO -> {
                if (appRequest.getFields().stream().anyMatch(f -> f.getMinSize() != null || f.getMaxSize() != null)) {
                    code.append("import jakarta.validation.constraints.Size;\n");
                }
            }

            case CONTROLLER -> code.append("import jakarta.validation.Valid;\n");
        }

    }

    @Override
    public void fieldAnnotationCodeBlock(AppRequest appRequest, FileBuilderType type, Field field, Code code) {

        if (type != FileBuilderType.DTO) {
            return;
        }

        if (field.getMinSize() == null && field.getMaxSize() == null) {
            return;
        }

        code.append("\t@Size(");

        if (field.getMinSize() != null) {
            code.append("min = ");
            code.append(field.getMinSize());
            if (field.getMaxSize() != null) {
                code.append(", ");
            }
        }

        if (field.getMaxSize() != null) {
            code.append("max = ");
            code.append(field.getMaxSize());
        }

        code.append(")\n");
    }

}
