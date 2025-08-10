package org.mrpaulwoods.backend.technology;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.backend.generate.dto.AppRequest;

import static org.junit.jupiter.api.Assertions.assertFalse;

class LombokTechnologyTests {

    StringBuilder sb = new StringBuilder();
    AppRequest appRequest = new AppRequest();
    LombokTechnology technology = new LombokTechnology();

    @Test
    void importCodeBlock_adds_imports() {

        technology.importCodeBlock(appRequest, sb);

        assertFalse(sb.isEmpty());
    }

    @Test
    void classAnnotationsCodeBlock_adds_annotations() {

        technology.classAnnotationsCodeBlock(appRequest, sb);

        assertFalse(sb.isEmpty());
    }

}