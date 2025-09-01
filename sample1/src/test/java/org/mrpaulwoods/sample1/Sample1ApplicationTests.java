package org.mrpaulwoods.sample1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.sample1.dto.UserDto;
import org.mrpaulwoods.sample1.entity.User;
import org.mrpaulwoods.sample1.mapper.UserMapper;
import org.mrpaulwoods.sample1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;
import java.util.UUID;

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
    public void list_successful() {

        UserDto dto1 = UserDto.builder()
                .firstName("first")
                .lastName("last")
                .build();

        userRepository.save(UserMapper.toEntity(dto1)).block();

        client.get()
                .uri("/v1/user")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$.[0].firstName").isEqualTo("first")
                .jsonPath("$.[0].lastName").isEqualTo("last")
                .jsonPath("$.[0].id").isNotEmpty();
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
                .expectStatus().isCreated()
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
                .expectStatus().isBadRequest();
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
                .expectStatus().isBadRequest();
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
                .expectStatus().isBadRequest();
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
                .expectStatus().isBadRequest();
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
                .expectStatus().isBadRequest();
    }

    @Test
    public void read_successful() {

        User entity1 = userRepository.save(User.builder()
                .firstName("first")
                .lastName("last")
                .build()).block();

        Assertions.assertNotNull(entity1);
        Assertions.assertNotNull(entity1.getId());

        client.get()
                .uri("/v1/user/{id}", Map.of("id", entity1.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(entity1.getId().toString())
                .jsonPath("$.firstName").isEqualTo("first")
                .jsonPath("$.lastName").isEqualTo("last");
    }

    @Test
    public void read_fails_when_not_found() {

        client.get()
                .uri("/v1/user/{id}", Map.of("id", UUID.randomUUID()))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void update_successful() {

        User entity1 = userRepository.save(User.builder()
                .firstName("first1")
                .lastName("last1")
                .build()).block();

        Assertions.assertNotNull(entity1);
        Assertions.assertNotNull(entity1.getId());

        UserDto dto2 = UserDto.builder()
                .firstName("first2")
                .lastName("last2")
                .build();

        client.put()
                .uri("/v1/user/{id}", Map.of("id", entity1.getId()))
                .bodyValue(dto2)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(entity1.getId().toString())
                .jsonPath("$.firstName").isEqualTo("first2")
                .jsonPath("$.lastName").isEqualTo("last2");
    }

    @Test
    public void update_fails_when_not_found() {

        UserDto dto2 = UserDto.builder()
                .firstName("first2")
                .lastName("last2")
                .build();

        client.put()
                .uri("/v1/user/{id}", Map.of("id", UUID.randomUUID()))
                .bodyValue(dto2)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void update_fails_when_body_null() {

        User entity1 = userRepository.save(User.builder()
                .firstName("first1")
                .lastName("last1")
                .build()).block();

        Assertions.assertNotNull(entity1);
        Assertions.assertNotNull(entity1.getId());

        client.put()
                .uri("/v1/user/{id}", Map.of("id", entity1.getId()))
                .exchange()
                .expectStatus().isBadRequest();
    }

//    @Test
//    void update_fails_when_ids_dont_match() {
//
//        User entity1 = userRepository.save(User.builder()
//                .firstName("first1")
//                .lastName("last1")
//                .build()).block();
//
//        Assertions.assertNotNull(entity1);
//        Assertions.assertNotNull(entity1.getId());
//
//        User entity2 = userRepository.save(User.builder()
//                .firstName("first2")
//                .lastName("last2")
//                .build()).block();
//
//        Assertions.assertNotNull(entity2);
//        Assertions.assertNotNull(entity2.getId());
//
//        UserDto dto2 = UserMapper.toDto(entity2);
//
//        // url id is for entity1. body id is for entity2.
//        client.put()
//                .uri("/v1/user/{id}", Map.of("id", entity1.getId()))
//                .bodyValue(dto2)
//                .exchange()
//                .expectStatus().isBadRequest();
//    }


    @Test
    public void delete_successful() {

        User entity1 = userRepository.save(User.builder()
                .firstName("first")
                .lastName("last")
                .build()).block();

        Assertions.assertNotNull(entity1);
        Assertions.assertNotNull(entity1.getId());

        client.delete()
                .uri("/v1/user/{id}", Map.of("id", entity1.getId()))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void delete_fails_when_not_found() {

        client.get()
                .uri("/v1/user/{id}", Map.of("id", UUID.randomUUID()))
                .exchange()
                .expectStatus().isNotFound();
    }

}
