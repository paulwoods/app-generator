package org.mrpaulwoods.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BackendApplicationTests {

    private final String results1 = """
            //file: src/main/java/org/mrpaulwoods/application/entity/User.java
            
            package org.mrpaulwoods.application.entity;
            
            import lombok.*;
            import org.springframework.data.annotation.Id;
            import java.util.UUID;
            
            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public class User {
            
            	@Id
            	private UUID id;
            
            	private String name;
            
            }
            //file: src/main/java/org/mrpaulwoods/application/dto/UserDto.java
            
            package org.mrpaulwoods.application.dto;
            
            import lombok.*;
            import java.util.UUID;
            import jakarta.validation.constraints.Size;
            
            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public class UserDto {
            
            	private UUID id;
            
            	@Size(max = 100)
            	private String name;
            
            }
            """;
    WebClient webClient;
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void create() {

        AppRequest appRequest = AppRequest.builder()
                .entity("User")
                .pkg("org.mrpaulwoods.application")
                .field(Field.builder()
                        .name("id")
                        .type("UUID")
                        .id(true)
                        .build())
                .field(Field.builder()
                        .name("name")
                        .type("String")
                        .maxSize(100)
                        .build())
                .build();

        webClient.post()
                .uri("/v1/generate")
                .bodyValue(appRequest)
                .retrieve()
                .bodyToMono(String.class)
                .as(StepVerifier::create)
                .assertNext(s -> Assertions.assertEquals(results1, s))
                .verifyComplete()
        ;
    }

}
