package org.mrpaulwoods.backend.technology;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.types.FileBuilderType;

import static org.junit.jupiter.api.Assertions.assertFalse;

class LombokTechnologyTests {

    Code code = new Code();
    AppRequest appRequest = new AppRequest();
    LombokTechnology technology = new LombokTechnology();

    @Test
    void importCodeBlock_adds_imports() {

        technology.importCodeBlock(appRequest, FileBuilderType.DTO, code);

        assertFalse(code.isEmpty());
    }

    @Test
    void classAnnotationsCodeBlock_adds_annotations() {

        technology.classAnnotationsCodeBlock(appRequest, FileBuilderType.DTO, code);

        assertFalse(code.isEmpty());
    }

}