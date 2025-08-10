package org.mrpaulwoods.backend.export;

import lombok.RequiredArgsConstructor;
import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.technology.Technology;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.mrpaulwoods.backend.export.FileBuilderType.ENTITY;

@Component
@RequiredArgsConstructor
@Order(FileBuilder.ENTITY_ORDER)
public final class EntityFileBuilder implements FileBuilder {

    private final List<Technology> technologies;

    @Override
    public void build(AppRequest appRequest, Code code) {

        // filename
        code.append("//file: src/main/java/");
        code.append(appRequest.getPkgAsFolder());
        code.append("/entity/");
        code.append(appRequest.getClassName());
        code.append(".java\n\n");

        // package
        code.append("package ");
        code.append(appRequest.getPkg());
        code.append(".entity;\n\n");

        // imports
        technologies.forEach(t -> t.importCodeBlock(appRequest, ENTITY, code));
        code.append("\n");

        // annotations
        technologies.forEach(t -> t.classAnnotationsCodeBlock(appRequest, ENTITY, code));

        // class
        code.append("public class ");
        code.append(appRequest.getClassName());
        code.append(" {\n\n");

        // fields
        appRequest.getFields().forEach(field -> {

            // annotations
            technologies.forEach(t -> t.fieldAnnotationCodeBlock(appRequest, ENTITY, field, code));

            // field
            code.append("\tprivate ");
            code.append(field.getType());
            code.append(" ");
            code.append(field.getName());
            code.append(";\n\n");

        });

        // end class
        code.append("}\n");
    }

}
