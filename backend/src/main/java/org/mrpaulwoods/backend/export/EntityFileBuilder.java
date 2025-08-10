package org.mrpaulwoods.backend.export;

import lombok.RequiredArgsConstructor;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.technology.Technology;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public final class EntityFileBuilder implements FileBuilder {

    private final List<Technology> technologies;

    @Override
    public void build(AppRequest appRequest, StringBuilder sb) {

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
        technologies.forEach(t -> t.importCodeBlock(appRequest, sb));
        sb.append("\n");

        // annotations
        technologies.forEach(t -> t.classAnnotationsCodeBlock(appRequest, sb));

        // class
        sb.append("public class ");
        sb.append(appRequest.getClassName());
        sb.append(" {\n\n");

        // id
//        sb.append("\t@Id\n");
//        sb.append("\tprivate UUID id;\n\n");

        // fields
        appRequest.getFields().forEach(field -> {

            // annotations
            technologies.forEach(t -> t.fieldAnnotationCodeBlock(appRequest, field, sb));

            // field
            sb.append("\tprivate ");
            sb.append(field.getType());
            sb.append(" ");
            sb.append(field.getName());
            sb.append(";\n\n");

        });

        // end class
        sb.append("}\n");
    }

}
