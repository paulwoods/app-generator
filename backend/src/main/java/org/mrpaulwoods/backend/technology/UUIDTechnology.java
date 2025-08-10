package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.springframework.stereotype.Component;

@Component
public class UUIDTechnology implements Technology {

    @Override
    public void addImports(AppRequest appRequest, StringBuilder sb) {
        sb.append("import java.util.UUID;\n");
    }

}
