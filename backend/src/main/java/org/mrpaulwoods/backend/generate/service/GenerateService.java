package org.mrpaulwoods.backend.generate.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.technology.Technology;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerateService {

    private final List<Technology> technologies;

    public String generate(@Valid AppRequest appRequest) {
        log.debug("generate: {}", appRequest);

        //noinspection StringBufferReplaceableByString
        StringBuilder sb = new StringBuilder();

        // filename
        sb.append("//file: src/main/java/");
        sb.append(appRequest.getPkgAsFolder());
        sb.append("/entity/");
        sb.append(appRequest.getClassName());
        sb.append(".java\n\n");

        // package
        sb.append("package ");
        sb.append(appRequest.getPkg());
        sb.append(".entity;\n\n");

        // imports
        technologies.forEach(t -> t.addImports(appRequest, sb));
        sb.append("\n");

        // annotations
        sb.append("@Data\n");
        sb.append("@Builder\n");
        sb.append("@NoArgsConstructor\n");
        sb.append("@AllArgsConstructor\n");

        // class
        sb.append("public class ");
        sb.append(appRequest.getClassName());
        sb.append(" {\n\n");

        // id
        sb.append("\t@Id\n");
        sb.append("\tprivate UUID id;\n\n");

        // fields
        appRequest.getFields().forEach(f -> {

            // annotation
            if (f.getMinSize() != null || f.getMaxSize() != null) {

                sb.append("\t@Size(");

                if (f.getMinSize() != null) {
                    sb.append("min = ");
                    sb.append(f.getMinSize());
                    if (f.getMaxSize() != null) {
                        sb.append(", ");
                    }
                }

                if (f.getMaxSize() != null) {
                    sb.append("max = ");
                    sb.append(f.getMaxSize());
                }

                sb.append(");\n");
            }

            // field
            sb.append("\tprivate ");
            sb.append(f.getType());
            sb.append(" ");
            sb.append(f.getName());
            sb.append(";\n\n");

        });

        // end class
        sb.append("}\n");

        return sb.toString();
    }

}
