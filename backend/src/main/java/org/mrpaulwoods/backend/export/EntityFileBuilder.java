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
        FileBuilderUtil.buildFileName("src", "entity", "", appRequest, code);

        // package
        FileBuilderUtil.appendPackage("entity", appRequest, code);

        // imports
        FileBuilderUtil.appendImports(technologies, ENTITY, appRequest, code);

        // annotations
        FileBuilderUtil.appendClassAnnotations(technologies, ENTITY, appRequest, code);

        // class
        FileBuilderUtil.appendClass("", appRequest, code);

        // fields
        appRequest.getFields().forEach(field -> {

            // annotations
            FileBuilderUtil.appendFieldAnnotations(technologies, ENTITY, appRequest, field, code);

            // field
            FileBuilderUtil.appendField(field.getType(), field.getName(), code);

        });

        // end class
        code.append("}\n");
    }

}
