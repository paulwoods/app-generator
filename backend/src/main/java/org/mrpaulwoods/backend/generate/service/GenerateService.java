package org.mrpaulwoods.backend.generate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        StringBuilder sb = new StringBuilder();
        fileBuilders.forEach(builder -> builder.build(appRequest, sb));
        return sb.toString();
    }

}
