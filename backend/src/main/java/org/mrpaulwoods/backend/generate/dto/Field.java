package org.mrpaulwoods.backend.generate.dto;

import lombok.Data;

@Data
public class Field {
    private String name;
    private String type;
    private Integer minSize;
    private Integer maxSize;
}
