package org.mrpaulwoods.backend.technology;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.export.FileBuilderType;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UUIDTechnologyTests {

    Code code = new Code();
    AppRequest appRequest = new AppRequest();
    UUIDTechnology technology = new UUIDTechnology();

    @Test
    void importCodeBlock_adds_imports() {
        Field field = Field.builder()
                .name("name")
                .type("UUID")
                .build();

        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(field);

        technology.importCodeBlock(appRequest, FileBuilderType.DTO, code);

        assertFalse(code.isEmpty());
    }

    @Test
    void importCodeBlock_doesnt_add_if_no_UUID_field() {
        Field field = Field.builder()
                .name("name")
                .type("Long")
                .build();

        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(field);

        technology.importCodeBlock(appRequest, FileBuilderType.DTO, code);

        assertTrue(code.isEmpty());
    }

}
