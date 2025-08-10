package org.mrpaulwoods.backend.technology;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTechnologyTests {

    StringBuilder sb = new StringBuilder();
    AppRequest appRequest = new AppRequest();
    ValidationTechnology technology = new ValidationTechnology();

    @Test
    void importCodeBlock_import_added_min() {
        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(new Field("name", "String", 1, null));

        technology.importCodeBlock(appRequest, sb);

        assertFalse(sb.isEmpty());
    }

    @Test
    void importCodeBlock_import_added_max() {
        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(new Field("name", "String", null, 1));

        technology.importCodeBlock(appRequest, sb);

        assertFalse(sb.isEmpty());
    }

    @Test
    void importCodeBlock_import_added_min_and_max() {
        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(new Field("name", "String", 1, 2));

        technology.importCodeBlock(appRequest, sb);

        assertFalse(sb.isEmpty());
    }

    @Test
    void importCodeBlock_import_not_added() {
        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(new Field("name", "String", null, null));

        technology.importCodeBlock(appRequest, sb);

        assertTrue(sb.isEmpty());
    }

    @Test
    void fieldAnnotationCodeBlock_annotation_added_min() {
        Field field = new Field("name", "String", 1, null);

        technology.fieldAnnotationCodeBlock(appRequest, field, sb);

        assertEquals("\t@Size(min = 1);\n", sb.toString());
    }

    @Test
    void fieldAnnotationCodeBlock_annotation_added_max() {
        Field field = new Field("name", "String", null, 1);

        technology.fieldAnnotationCodeBlock(appRequest, field, sb);

        assertEquals("\t@Size(max = 1);\n", sb.toString());
    }

    @Test
    void fieldAnnotationCodeBlock_annotation_added_min_and_max() {
        Field field = new Field("name", "String", 1, 2);

        technology.fieldAnnotationCodeBlock(appRequest, field, sb);

        assertEquals("\t@Size(min = 1, max = 2);\n", sb.toString());
    }

    @Test
    void fieldAnnotationCodeBlock_annotation_not_added() {
        Field field = new Field("name", "String", null, null);

        technology.fieldAnnotationCodeBlock(appRequest, field, sb);

        assertEquals(0, sb.toString().length());
    }

}
