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

    int ENTITY_ORDER = 10;
    int DTO_ORDER = 20;
    int MAPPER_ORDER = 30;
    int REPOSITORY_ORDER = 40;
    int NOT_FOUND_EXCEPTION_ORDER = 50;
    int SERVICE_ORDER = 60;
    int CONTROLLER_ORDER = 70;

    FileBuilderName getName();

    Code build(AppRequest appRequest);
}
