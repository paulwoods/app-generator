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

    public void append(String s) {
        content.append(s);
    }

    public void append(Integer i) {
        content.append(i);
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }

    public String getContentAsString() {
        return content.toString();
    }

    public int length() {
        return content.length();
    }
}
