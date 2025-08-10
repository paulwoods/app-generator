package org.mrpaulwoods.backend.generate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.service.GenerateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/generate")
@Slf4j
@RequiredArgsConstructor
public class GenerateController {

    private final GenerateService generateService;

    @PostMapping
    public String generate(@Valid @RequestBody AppRequest appRequest) {
        log.info("generate: {}", appRequest);
        return generateService.generate(appRequest);
    }
}
