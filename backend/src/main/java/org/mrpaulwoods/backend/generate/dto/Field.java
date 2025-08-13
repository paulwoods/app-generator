package org.mrpaulwoods.backend.generate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Field {
    private String name;
    private String type;
    private Integer minSize;
    private Integer maxSize;
    private boolean id;

    public String getGetterName() {
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String getSetterName() {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
