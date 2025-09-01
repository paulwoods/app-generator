package org.mrpaulwoods.backend.generate.dto;

import org.mrpaulwoods.backend.Code;

import java.util.List;

public record GenerateResults(
        List<Code> codes
) {

    public Code code(int index) {
        return codes.get(index);
    }


}
