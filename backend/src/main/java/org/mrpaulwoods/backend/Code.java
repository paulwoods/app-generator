package org.mrpaulwoods.backend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Code {
    private final StringBuilder content = new StringBuilder();
    private String fileName;

    public void append(String text) {
        content.append(text);
    }
}
