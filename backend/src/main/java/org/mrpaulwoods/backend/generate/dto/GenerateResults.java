package org.mrpaulwoods.backend.generate.dto;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.utils.FileBuilderName;

import java.util.List;

public record GenerateResults(
        List<Code> codes
) {

    public Code code(int index) {
        return codes.get(index);
    }

    public Code code(FileBuilderName fileBuilderName) {
        return codes.stream()
                .filter(c -> c.getName() == fileBuilderName)
                .findFirst()
                .orElse(null);
    }

}
