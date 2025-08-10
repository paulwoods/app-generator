package org.mrpaulwoods.backend.generate.dto;

import lombok.Data;

@Data
public class AppRequest {
    private String entity;
    private String pkg;
    private String fields;
}
