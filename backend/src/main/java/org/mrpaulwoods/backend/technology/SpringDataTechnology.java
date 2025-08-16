package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.export.FileBuilderType;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;
import org.springframework.stereotype.Component;

@Component
public final class SpringDataTechnology implements Technology {

    @Override
    public void importCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {

        switch (type) {
            case ENTITY -> {
                if (appRequest.getFields().stream().anyMatch(Field::isId)) {
                    code.append("import org.springframework.data.annotation.Id;\n");
                }
            }
            case REPOSITORY -> {
                code.append("import org.springframework.data.repository.reactive.ReactiveCrudRepository;\n");
            }
        }

    }

    @Override
    public void fieldAnnotationCodeBlock(AppRequest appRequest, FileBuilderType type, Field field, Code code) {

        switch (type) {
            case ENTITY -> {
                if (field.isId()) {
                    code.append("\t@Id\n");
                }
            }
        }

    }

}
