package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.generate.dto.AppRequest;

public interface Technology {
    void addImports(AppRequest appRequest, StringBuilder sb);
}
