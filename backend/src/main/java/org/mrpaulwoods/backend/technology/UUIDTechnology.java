package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

@Component
public final class UUIDTechnology implements Technology {

    @Override
    public void importCodeBlock(AppRequest appRequest, StringBuilder sb) {

        if (appRequest.getFields().stream().anyMatch(f ->
                equalsIgnoreCase(f.getType(), "UUID") || endsWithIgnoreCase(f.getType(), ".UUID"))) {
            sb.append("import java.util.UUID;\n");
        }

    }

}
