package org.mrpaulwoods.backend.generate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field {
    private String name;
    private String type;
    private Integer minSize;
    private Integer maxSize;
}
