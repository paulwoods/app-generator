package org.mrpaulwoods.backend.filebuilder;

import lombok.RequiredArgsConstructor;
import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.technology.Technology;
import org.mrpaulwoods.backend.utils.FileBuilderName;
import org.mrpaulwoods.backend.utils.FileBuilderUtil;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.mrpaulwoods.backend.types.FileBuilderType.ENTITY;
import static org.mrpaulwoods.backend.utils.Constants.ENTITY_OBJECT;

@Component
@RequiredArgsConstructor
public final class EntityFileBuilder implements FileBuilder {

    private final List<Technology> technologies;

    @Override
    public FileBuilderName getName() {
        return FileBuilderName.ENTITY;
    }

    @Override
    public Code build(AppRequest appRequest) {

        Code code = Code.builder().name(getName()).build();

        // filename
        code.setFileName(FileBuilderUtil.createSourceFilename(FileBuilderUtil.absoluteEntity(appRequest)));

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

        return code;
    }

}
