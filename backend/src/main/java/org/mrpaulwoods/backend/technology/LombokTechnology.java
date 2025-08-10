package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.springframework.stereotype.Component;

@Component
public class LombokTechnology implements Technology {

    @Override
    public void addImports(AppRequest appRequest, StringBuilder sb) {
        sb.append("import lombok.*;\n");
    }

    @Override
    public void addClassAnnotations(AppRequest appRequest, StringBuilder sb) {
        sb.append("@Data\n");
        sb.append("@Builder\n");
        sb.append("@NoArgsConstructor\n");
        sb.append("@AllArgsConstructor\n");
    }

}
