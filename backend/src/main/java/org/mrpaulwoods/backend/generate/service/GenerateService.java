package org.mrpaulwoods.backend.generate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.export.FileBuilder;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerateService {

    private final List<FileBuilder> fileBuilders;

    public String generate(AppRequest appRequest) {
        log.debug("generate: {}", appRequest);

        List<Code> codes = fileBuilders.stream()
                .map(builder -> new BuilderCodePair(builder, new Code()))
                .peek(pair -> pair.builder().build(appRequest, pair.code()))
                .map(BuilderCodePair::code)
                .toList();

        StringBuilder sb = new StringBuilder();
        codes.forEach(c -> {
            sb.append("//file: ");
            sb.append(c.getFileName());
            sb.append("\n\n");
            sb.append(c.getContent().toString());
        });
        return sb.toString();
    }

    record BuilderCodePair(FileBuilder builder, Code code) {
    }

}

// builder.build(appRequest, sb)
