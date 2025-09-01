package org.mrpaulwoods.backend.filebuilder;

import lombok.RequiredArgsConstructor;
import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.technology.Technology;
import org.mrpaulwoods.backend.utils.FileBuilderName;
import org.mrpaulwoods.backend.utils.FileBuilderUtil;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.mrpaulwoods.backend.types.FileBuilderType.DTO;
import static org.mrpaulwoods.backend.utils.Constants.DTO_OBJECT;

@Component
@RequiredArgsConstructor
public final class DtoFileBuilder implements FileBuilder {

    private final List<Technology> technologies;

    @Override
    public FileBuilderName getName() {
        return FileBuilderName.DTO;
    }

    @Override
    public Code build(AppRequest appRequest) {

        Code code = Code.builder().name(getName()).build();

        // filename
        code.setFileName(FileBuilderUtil.createSourceFilename(FileBuilderUtil.absoluteDto(appRequest)));

        // package
        FileBuilderUtil.appendPackage(DTO_OBJECT, appRequest, code);

        // imports
        FileBuilderUtil.appendImports(technologies, DTO, appRequest, code);

        // class annotations
        FileBuilderUtil.appendClassAnnotations(technologies, DTO, appRequest, code);

        // class
        FileBuilderUtil.appendClass(appRequest.getDtoClassName(), "", code);

        // fields
        appRequest.getFields().forEach(field -> {

            // field annotations
            FileBuilderUtil.appendFieldAnnotations(technologies, DTO, appRequest, field, code);

            // field
            FileBuilderUtil.appendField(field.getType(), field.getName(), code);

        });

        // end class
        FileBuilderUtil.appendClassEnd(code);

        return code;
    }

}
