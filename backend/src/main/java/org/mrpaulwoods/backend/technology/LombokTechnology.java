package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.types.FileBuilderType;
import org.springframework.stereotype.Component;

@Component
public final class LombokTechnology implements Technology {

    @Override
    public void importCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {
        switch (type) {
            case ENTITY, DTO -> code.append("import lombok.*;\n");
            case MAPPER -> {
            }
        }
    }

    @Override
    public void classAnnotationsCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {
        switch (type) {
            case ENTITY, DTO -> {
                code.append("@Data\n");
                code.append("@Builder\n");
                code.append("@NoArgsConstructor\n");
                code.append("@AllArgsConstructor\n");
            }
            case MAPPER -> {
            }
        }
    }

}
