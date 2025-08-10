package org.mrpaulwoods.backend.generate.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerateService {

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
        sb.append("import lombok.*;\n");
        sb.append("import org.springframework.data.annotation.Id;\n");
        sb.append("import java.util.UUID;\n");
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
        sb.append("\tprivate UUID id;\n");

        // end class
        sb.append("}\n");

        return sb.toString();
    }

}
