package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.export.FileBuilderType;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.springframework.stereotype.Component;

@Component
public final class LombokTechnology implements Technology {

    @Override
    public void importCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {
        code.append("import lombok.*;\n");
    }

    @Override
    public void classAnnotationsCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {
        code.append("@Data\n");
        code.append("@Builder\n");
        code.append("@NoArgsConstructor\n");
        code.append("@AllArgsConstructor\n");
    }

}
