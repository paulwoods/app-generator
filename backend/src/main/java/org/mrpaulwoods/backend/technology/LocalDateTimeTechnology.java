package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.types.FileBuilderType;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

@Component
public final class LocalDateTimeTechnology implements Technology {

    @Override
    public void importCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {

        switch (type) {
            case ENTITY, DTO -> {

                if (appRequest.getFields().stream().anyMatch(f ->
                        equalsIgnoreCase(f.getType(), "LocalDateTime") || endsWithIgnoreCase(f.getType(), ".LocalDateTime"))) {
                    code.append("import java.time.LocalDateTime;\n");
                }

            }

        }

    }

}
