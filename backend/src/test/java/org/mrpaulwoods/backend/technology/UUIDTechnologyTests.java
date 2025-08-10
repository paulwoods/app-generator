package org.mrpaulwoods.backend.technology;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UUIDTechnologyTests {

    StringBuilder sb = new StringBuilder();
    AppRequest appRequest = new AppRequest();
    UUIDTechnology technology = new UUIDTechnology();

    @Test
    void importCodeBlock_adds_imports() {
        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(new Field("name", "UUID", null, null));

        technology.importCodeBlock(appRequest, sb);

        assertFalse(sb.isEmpty());
    }

    @Test
    void importCodeBlock_doesnt_add_if_no_UUID_field() {
        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(new Field("name", "Long", null, null));

        technology.importCodeBlock(appRequest, sb);

        assertTrue(sb.isEmpty());
    }

}
