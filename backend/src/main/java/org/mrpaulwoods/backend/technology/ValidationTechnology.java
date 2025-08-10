package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;
import org.springframework.stereotype.Component;

@Component
public class ValidationTechnology implements Technology {

    @Override
    public void addImports(AppRequest appRequest, StringBuilder sb) {

        if (appRequest.getFields().stream().anyMatch(f -> f.getMinSize() != null || f.getMaxSize() != null)) {
            sb.append("import jakarta.validation.constraints.Size;\n");
        }
    }

    @Override
    public void addFieldAnnotation(AppRequest appRequest, Field field, StringBuilder sb) {

        if (field.getMinSize() != null || field.getMaxSize() != null) {

            sb.append("\t@Size(");

            if (field.getMinSize() != null) {
                sb.append("min = ");
                sb.append(field.getMinSize());
                if (field.getMaxSize() != null) {
                    sb.append(", ");
                }
            }

            if (field.getMaxSize() != null) {
                sb.append("max = ");
                sb.append(field.getMaxSize());
            }

            sb.append(");\n");
        }

    }
}
