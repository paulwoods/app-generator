package org.mrpaulwoods.backend.technology;

import org.junit.jupiter.api.Test;
import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.export.FileBuilderType;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTechnologyTests {

    Code code = new Code();
    AppRequest appRequest = new AppRequest();
    ValidationTechnology technology = new ValidationTechnology();

    @Test
    void importCodeBlock_import_added_min() {
        Field field = Field.builder()
                .name("name")
                .type("String")
                .minSize(1)
                .build();

        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(field);

        technology.importCodeBlock(appRequest, FileBuilderType.DTO, code);

        assertFalse(code.isEmpty());
    }

    @Test
    void importCodeBlock_import_added_max() {
        Field field = Field.builder()
                .name("name")
                .type("String")
                .maxSize(1)
                .build();

        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(field);

        technology.importCodeBlock(appRequest, FileBuilderType.DTO, code);

        assertFalse(code.isEmpty());
    }

    @Test
    void importCodeBlock_import_added_min_and_max() {
        Field field = Field.builder()
                .name("name")
                .type("String")
                .maxSize(1)
                .maxSize(2)
                .build();

        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(field);

        technology.importCodeBlock(appRequest, FileBuilderType.DTO, code);

        assertFalse(code.isEmpty());
    }

    @Test
    void importCodeBlock_import_not_added() {
        Field field = Field.builder()
                .name("name")
                .type("String")
                .build();

        appRequest.setFields(new ArrayList<>());
        appRequest.getFields().add(field);

        technology.importCodeBlock(appRequest, FileBuilderType.DTO, code);

        assertTrue(code.isEmpty());
    }

    @Test
    void fieldAnnotationCodeBlock_annotation_added_min() {
        Field field = Field.builder()
                .name("name")
                .type("String")
                .minSize(1)
                .build();

        technology.fieldAnnotationCodeBlock(appRequest, FileBuilderType.DTO, field, code);

        assertEquals("\t@Size(min = 1);\n", code.getContentAsString());
    }

    @Test
    void fieldAnnotationCodeBlock_annotation_added_max() {
        Field field = Field.builder()
                .name("name")
                .type("String")
                .maxSize(1)
                .build();

        technology.fieldAnnotationCodeBlock(appRequest, FileBuilderType.DTO, field, code);

        assertEquals("\t@Size(max = 1);\n", code.getContentAsString());
    }

    @Test
    void fieldAnnotationCodeBlock_annotation_added_min_and_max() {
        Field field = Field.builder()
                .name("name")
                .type("String")
                .minSize(1)
                .maxSize(2)
                .build();

        technology.fieldAnnotationCodeBlock(appRequest, FileBuilderType.DTO, field, code);

        assertEquals("\t@Size(min = 1, max = 2);\n", code.getContentAsString());
    }

    @Test
    void fieldAnnotationCodeBlock_annotation_not_added() {
        Field field = Field.builder()
                .name("name")
                .type("String")
                .build();

        technology.fieldAnnotationCodeBlock(appRequest, FileBuilderType.DTO, field, code);

        assertEquals(0, code.length());
    }

}
