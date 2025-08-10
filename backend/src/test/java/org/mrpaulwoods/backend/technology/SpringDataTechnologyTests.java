package org.mrpaulwoods.backend.technology;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.backend.export.FileBuilderType;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpringDataTechnologyTests {

    StringBuilder sb = new StringBuilder();
    AppRequest appRequest = new AppRequest();
    SpringDataTechnology technology = new SpringDataTechnology();

    @Test
    void importCodeBlock_adds_imports() {
        Field field = Field.builder()
                .id(true)
                .build();

        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(field);

        technology.importCodeBlock(appRequest, FileBuilderType.ENTITY, sb);

        assertFalse(sb.isEmpty());
    }

    @Test
    void importCodeBlock_doesnt_add_imports() {
        Field field = Field.builder()
                .build();

        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(field);

        technology.importCodeBlock(appRequest, FileBuilderType.ENTITY, sb);

        assertTrue(sb.isEmpty());
    }

}