package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.types.FileBuilderType;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

@Component
public final class UUIDTechnology implements Technology {

    @Override
    public void importCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {

        if (appRequest.getFields().stream().anyMatch(f ->
                equalsIgnoreCase(f.getType(), "UUID") || endsWithIgnoreCase(f.getType(), ".UUID"))) {
            code.append("import java.util.UUID;\n");
        }

    }

}
