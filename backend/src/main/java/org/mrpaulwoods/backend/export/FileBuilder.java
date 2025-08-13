package org.mrpaulwoods.backend.export;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;

public sealed interface FileBuilder permits DtoFileBuilder, EntityFileBuilder, MapperFileBuilder {

    int ENTITY_ORDER = 10;
    int DTO_ORDER = 20;
    int MAPPER_ORDER = 30;

    void build(AppRequest appRequest, Code code);
}
