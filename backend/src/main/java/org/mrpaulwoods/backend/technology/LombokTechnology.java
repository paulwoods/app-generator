package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.export.FileBuilderType;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.springframework.stereotype.Component;

@Component
public final class LombokTechnology implements Technology {

    @Override
    public void importCodeBlock(AppRequest appRequest, FileBuilderType type, StringBuilder sb) {
        sb.append("import lombok.*;\n");
    }

    @Override
    public void classAnnotationsCodeBlock(AppRequest appRequest, FileBuilderType type, StringBuilder sb) {
        sb.append("@Data\n");
        sb.append("@Builder\n");
        sb.append("@NoArgsConstructor\n");
        sb.append("@AllArgsConstructor\n");
    }

}
