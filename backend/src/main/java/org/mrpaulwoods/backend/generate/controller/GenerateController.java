package org.mrpaulwoods.backend.generate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/generate")
@Slf4j
public class GenerateController {

    @PostMapping
    public void generate(@Valid @RequestBody AppRequest appRequest) {
        log.info("generate: {}", appRequest);
    }
}
