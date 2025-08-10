package org.mrpaulwoods.backend.export;

import lombok.RequiredArgsConstructor;
import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.technology.Technology;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.mrpaulwoods.backend.export.FileBuilderType.DTO;

@Component
@RequiredArgsConstructor
@Order(FileBuilder.DTO_ORDER)
public final class DtoFileBuilder implements FileBuilder {

    private final List<Technology> technologies;

    @Override
    public void build(AppRequest appRequest, Code code) {
        StringBuilder sb = code.getContent();

        // filename

        sb.append("//file: src/main/java/");
        sb.append(appRequest.getPkgAsFolder());
        sb.append("/dto/");
        sb.append(appRequest.getClassName());
        sb.append("Dto.java\n\n");

        // package
        sb.append("package ");
        sb.append(appRequest.getPkg());
        sb.append(".dto;\n\n");

        // imports
        technologies.forEach(t -> t.importCodeBlock(appRequest, DTO, sb));
        sb.append("\n");

        // annotations
        technologies.forEach(t -> t.classAnnotationsCodeBlock(appRequest, DTO, sb));

        // class
        sb.append("public class ");
        sb.append(appRequest.getClassName());
        sb.append("Dto {\n\n");

        // fields
        appRequest.getFields().forEach(field -> {

            // annotations
            technologies.forEach(t -> t.fieldAnnotationCodeBlock(appRequest, DTO, field, sb));

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
