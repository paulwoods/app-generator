package org.mrpaulwoods.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mrpaulwoods.backend.utils.FileBuilderName;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Code {

    private FileBuilderName name;
    private String fileName;
    private final StringBuilder content = new StringBuilder();

    public void append(String s) {
        content.append(s);
    }

    public void append(Integer i) {
        content.append(i);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return content.isEmpty();
    }

    @JsonIgnore
    public String getContentAsString() {
        return content.toString();
    }

    public int length() {
        return content.length();
    }
}
