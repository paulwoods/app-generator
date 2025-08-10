package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.export.FileBuilderType;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;
import org.springframework.stereotype.Component;

@Component
public final class SpringDataTechnology implements Technology {

    @Override
    public void importCodeBlock(AppRequest appRequest, FileBuilderType type, StringBuilder sb) {

        switch (type) {
            case ENTITY -> {
                if (appRequest.getFields().stream().anyMatch(Field::isId)) {
                    sb.append("import org.springframework.data.annotation.Id;\n");
                }
            }
            case DTO -> {
            }
        }

    }

    @Override
    public void fieldAnnotationCodeBlock(AppRequest appRequest, FileBuilderType type, Field field, StringBuilder sb) {

        switch (type) {
            case ENTITY -> {
                if (field.isId()) {
                    sb.append("\t@Id\n");
                }
            }
            case DTO -> {
            }
        }

    }

}
