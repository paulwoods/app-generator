package org.mrpaulwoods.backend;

import org.junit.jupiter.api.BeforeEach;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.GenerateResults;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

    protected WebClient webClient;

    @LocalServerPort
    protected int port;

    protected AppRequest appRequest;

    @BeforeEach
    void setUp() {
        webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    protected StepVerifier.FirstStep<GenerateResults> post() {
        return webClient.post()
                .uri("/v1/generate")
                .bodyValue(appRequest)
                .retrieve()
                .bodyToMono(GenerateResults.class)
                .as(StepVerifier::create);
    }

}
