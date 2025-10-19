package org.mrpaulwoods.backend.generate.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.GenerateResults;
import org.mrpaulwoods.backend.generate.service.GenerateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/v1/generate")
@Slf4j
public class GenerateController {

    private final GenerateService generateService;
    private final MeterRegistry registry;
    private final Counter processedCounter;
    private final Timer processingTimer;

    public GenerateController(
            GenerateService generateService,
            MeterRegistry registry
    ) {
        this.generateService = generateService;
        this.registry = registry;

        this.processedCounter = Counter
                .builder("generate.controller.generate.count")
                .description("Total greetings processed")
                .tag("component", "service")
                .register(registry);

        this.processingTimer = Timer
                .builder("generate.controller.generate.timer")
                .description("Greeting processing duration")
                .tag("component", "service")
                .publishPercentileHistogram(true)     // enables histogram buckets for Prometheus
                .maximumExpectedValue(Duration.ofSeconds(5))
                .register(registry);
    }

    @PostMapping
    public Mono<GenerateResults> generate(@Valid @RequestBody AppRequest appRequest) {
        log.info("generate: {}", appRequest);
        processedCounter.increment();
        return processingTimer.record(() -> generateService.generate(appRequest));
    }
    
}
