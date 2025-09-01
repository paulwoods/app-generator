package org.mrpaulwoods.backend.generate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.filebuilder.FileBuilder;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.GenerateResults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerateService {

    private final List<FileBuilder> fileBuilders;

    public Mono<GenerateResults> generate(AppRequest appRequest) {
        log.debug("generate: {}", appRequest);

        return Mono.create(sink -> {

            List<Code> codes = fileBuilders.stream()
                    .map(builder -> builder.build(appRequest))
                    .toList();

//            List<Code> codes = fileBuilders.stream()
//                    .map(builder -> new BuilderCodePair(builder, new Code()))
//                    .peek(pair -> pair.builder().build(appRequest, pair.code()))
//                    .map(BuilderCodePair::code)
//                    .toList();

            GenerateResults results = new GenerateResults(codes);

            sink.success(results);
        });

    }

}

