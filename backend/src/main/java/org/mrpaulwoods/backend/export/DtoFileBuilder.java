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

        // filename
        FileBuilderUtil.buildFileName("src", "dto", "Dto", appRequest, code);

        // package
        FileBuilderUtil.appendPackage("dto", appRequest, code);

        // imports
        FileBuilderUtil.appendImports(technologies, DTO, appRequest, code);

        // annotations
        FileBuilderUtil.appendClassAnnotations(technologies, DTO, appRequest, code);

        // class
        FileBuilderUtil.appendClass("Dto", appRequest, code);

        // fields
        appRequest.getFields().forEach(field -> {

            // annotations
            FileBuilderUtil.appendFieldAnnotations(technologies, DTO, appRequest, field, code);

            // field
            FileBuilderUtil.appendField(field.getType(), field.getName(), code);

        });

        // end class
        FileBuilderUtil.appendClassEnd(code);
    }

}
