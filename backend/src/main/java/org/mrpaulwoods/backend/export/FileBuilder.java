package org.mrpaulwoods.backend.export;

import org.mrpaulwoods.backend.generate.dto.AppRequest;

public sealed interface FileBuilder permits DtoFileBuilder, EntityFileBuilder {

    int ENTITY_ORDER = 1;
    int DTO_ORDER = 2;

    void build(AppRequest appRequest, StringBuilder sb);
}
