package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.springframework.stereotype.Component;

@Component
public class ValidationTechnology implements Technology {

    @Override
    public void addImports(AppRequest appRequest, StringBuilder sb) {

        if (appRequest.getFields().stream().anyMatch(f -> f.getMinSize() != null || f.getMaxSize() != null)) {
            sb.append("import jakarta.validation.constraints.Size;\n");
        }
    }

}
