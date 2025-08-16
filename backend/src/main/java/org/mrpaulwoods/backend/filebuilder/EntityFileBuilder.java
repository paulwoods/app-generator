package org.mrpaulwoods.backend.filebuilder;

import lombok.RequiredArgsConstructor;
import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.technology.Technology;
import org.mrpaulwoods.backend.utils.FileBuilderUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.mrpaulwoods.backend.types.FileBuilderType.ENTITY;
import static org.mrpaulwoods.backend.utils.Constants.ENTITY_OBJECT;
import static org.mrpaulwoods.backend.utils.Constants.SOURCE_FOLDER;

@Component
@RequiredArgsConstructor
@Order(FileBuilder.ENTITY_ORDER)
public final class EntityFileBuilder implements FileBuilder {

    private final List<Technology> technologies;

    @Override
    public void build(AppRequest appRequest, Code code) {

        // filename
        FileBuilderUtil.buildFileName(SOURCE_FOLDER, ENTITY_OBJECT, appRequest.getEntityClassName(), "", appRequest, code);

        // package
        FileBuilderUtil.appendPackage(ENTITY_OBJECT, appRequest, code);

        // imports
        FileBuilderUtil.appendImports(technologies, ENTITY, appRequest, code);

        // class annotations
        FileBuilderUtil.appendClassAnnotations(technologies, ENTITY, appRequest, code);

        // class
        FileBuilderUtil.appendClass(appRequest.getEntityClassName(), "", code);

        // fields
        appRequest.getFields().forEach(field -> {

            // field annotations
            FileBuilderUtil.appendFieldAnnotations(technologies, ENTITY, appRequest, field, code);

            // field
            FileBuilderUtil.appendField(field.getType(), field.getName(), code);

        });

        // end class
        FileBuilderUtil.appendClassEnd(code);
    }

}
