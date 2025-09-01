package org.mrpaulwoods.backend.filebuilder;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.utils.FileBuilderName;

public sealed interface FileBuilder permits
        DtoFileBuilder,
        EntityFileBuilder,
        MapperFileBuilder,
        NotFoundExceptionFileBuilder,
        RepositoryFileBuilder,
        ServiceFileBuilder,
        ControllerFileBuilder {

    FileBuilderName getName();

    Code build(AppRequest appRequest);
}
