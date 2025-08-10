package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;
import org.springframework.stereotype.Component;

@Component
public final class SpringDataTechnology implements Technology {

    @Override
    public void importCodeBlock(AppRequest appRequest, StringBuilder sb) {

        if (appRequest.getFields().stream().anyMatch(Field::isId)) {
            sb.append("import org.springframework.data.annotation.Id;\n");
        }

    }

    @Override
    public void fieldAnnotationCodeBlock(AppRequest appRequest, Field field, StringBuilder sb) {

        if (field.isId()) {
            sb.append("\t@Id\n");
        }

    }

}
