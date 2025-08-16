package org.mrpaulwoods.sample1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.sample1.dto.UserDto;
import org.mrpaulwoods.sample1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class Sample1ApplicationTests {

    @Autowired
    private WebTestClient client;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll().block();
    }

    @Test
    void create_successful() {

        UserDto dto1 = UserDto.builder()
                .firstName("first")
                .lastName("last")
                .build();

        client.post()
                .uri("/v1/user")
                .bodyValue(dto1)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("first")
                .jsonPath("$.lastName").isEqualTo("last")
                .jsonPath("$.id").isNotEmpty();
    }

    @Test
    void create_fails_when_body_null() {
        client.post()
                .uri("/v1/user")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void create_fails_when_firstname_null() {

        UserDto dto1 = UserDto.builder()
                .firstName(null)
                .lastName("last")
                .build();

        client.post()
                .uri("/v1/user")
                .bodyValue(dto1)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void create_fails_when_firstname_too_long() {

        UserDto dto1 = UserDto.builder()
                .firstName("*".repeat(101))
                .lastName("last")
                .build();

        client.post()
                .uri("/v1/user")
                .bodyValue(dto1)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void create_fails_when_lastname_null() {

        UserDto dto1 = UserDto.builder()
                .firstName("first")
                .lastName(null)
                .build();

        client.post()
                .uri("/v1/user")
                .bodyValue(dto1)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void create_fails_when_lastname_too_long() {

        UserDto dto1 = UserDto.builder()
                .firstName("first")
                .lastName("*".repeat(101))
                .build();

        client.post()
                .uri("/v1/user")
                .bodyValue(dto1)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
