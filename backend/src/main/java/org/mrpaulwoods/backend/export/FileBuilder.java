package org.mrpaulwoods.backend.export;

import org.mrpaulwoods.backend.generate.dto.AppRequest;

public sealed interface FileBuilder permits EntityFileBuilder {
    void build(AppRequest appRequest, StringBuilder sb);
}
