package org.mrpaulwoods.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;
import org.mrpaulwoods.backend.generate.dto.GenerateResults;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import static org.mrpaulwoods.backend.utils.FileBuilderName.*;

public class SmokeTestsIT extends BaseTest {

    WebClient webClient;
    @LocalServerPort
    private int port;

    private AppRequest appRequest;

    @BeforeEach
    void setUp() {
        webClient = WebClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();

        appRequest = AppRequest.builder()
                .entity("User")
                .pkg("org.mrpaulwoods.application")
                .field(Field.builder()
                        .name("id")
                        .type("Long")
                        .id(true)
                        .build())
                .field(Field.builder()
                        .name("name")
                        .type("String")
                        .build())
                .build();
    }

    public StepVerifier.FirstStep<GenerateResults> post() {
        return webClient.post()
                .uri("/v1/generate")
                .bodyValue(appRequest)
                .retrieve()
                .bodyToMono(GenerateResults.class)
                .as(StepVerifier::create);
    }

    @Test
    void entity() {

        post().assertNext(gr -> {

                    Assertions.assertEquals(7, gr.codes().size());

                    Code code = gr.code(ENTITY);
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/entity/User.java", code.getFileName());
                    Assertions.assertEquals("""
                            package org.mrpaulwoods.application.entity;
                            
                            import lombok.*;
                            import org.springframework.data.annotation.Id;
                            
                            @Data
                            @Builder
                            @NoArgsConstructor
                            @AllArgsConstructor
                            public class User {
                            
                            \t@Id
                            \tprivate Long id;
                            
                            \tprivate String name;
                            
                            }
                            
                            """, code.getContentAsString());
                })
                .verifyComplete();
    }

    @Test
    void dto() {
        post().assertNext(gr -> {

                    Code code = gr.code(DTO);
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/dto/UserDto.java", code.getFileName());
                    Assertions.assertEquals("""
                            package org.mrpaulwoods.application.dto;
                            
                            import lombok.*;
                            
                            @Data
                            @Builder
                            @NoArgsConstructor
                            @AllArgsConstructor
                            public class UserDto {
                            
                            \tprivate Long id;
                            
                            \tprivate String name;
                            
                            }
                            
                            """, code.getContentAsString());
                })
                .verifyComplete();
    }

    @Test
    void mapper() {
        post().assertNext(gr -> {

                    Code code = gr.code(MAPPER);
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/mapper/UserMapper.java", code.getFileName());
                    Assertions.assertEquals("""
                            package org.mrpaulwoods.application.mapper;
                            
                            import org.mrpaulwoods.application.dto.UserDto;
                            import org.mrpaulwoods.application.entity.User;
                            
                            public class UserMapper {
                            
                            \tpublic static UserDto toDto(User entity) {
                            \t\tif (entity == null) {
                            \t\t\treturn null;
                            \t\t}
                            \t\treturn UserDto.builder()
                            \t\t\t.id(entity.getId())
                            \t\t\t.name(entity.getName())
                            \t\t\t.build();
                            \t}
                            
                            \tpublic static User toEntity(UserDto dto) {
                            \t\tif (dto == null) {
                            \t\t\treturn null;
                            \t\t}
                            \t\treturn User.builder()
                            \t\t\t.id(dto.getId())
                            \t\t\t.name(dto.getName())
                            \t\t\t.build();
                            \t}
                            
                            \tpublic static User update(UserDto dto, User entity) {
                            \t\tif (dto == null || entity == null) {
                            \t\t\treturn null;
                            \t\t}
                            \t\tentity.setName(dto.getName());
                            \t\treturn entity;
                            \t}
                            
                            }
                            
                            """, code.getContentAsString());
                })
                .verifyComplete();
    }

    @Test
    void repository() {
        post().assertNext(gr -> {

                    Code code = gr.code(REPOSITORY);
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/repository/UserRepository.java", code.getFileName());
                    Assertions.assertEquals("""
                            package org.mrpaulwoods.application.repository;
                            
                            import org.mrpaulwoods.application.entity.User;
                            import org.springframework.data.repository.reactive.ReactiveCrudRepository;
                            
                            public interface UserRepository extends ReactiveCrudRepository<User, Long> {
                            }
                            
                            """, code.getContentAsString());

                    code = gr.code(NOTFOUND);
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/exception/UserNotFoundException.java", code.getFileName());
                    Assertions.assertEquals("""
                            package org.mrpaulwoods.application.exception;
                            
                            public class UserNotFoundException extends RuntimeException {
                            
                            \tpublic UserNotFoundException(Long id) {
                            \t\tsuper("The user was not found: " + id);
                            \t}
                            
                            }
                            
                            """, code.getContentAsString());
                })
                .verifyComplete();
    }

    @Test
    void service() {
        post().assertNext(gr -> {

                    Code code = gr.code(SERVICE);
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/service/UserService.java", code.getFileName());
                    Assertions.assertEquals("""
                            package org.mrpaulwoods.application.service;
                            
                            import org.mrpaulwoods.application.dto.UserDto;
                            import org.mrpaulwoods.application.mapper.UserMapper;
                            import org.mrpaulwoods.application.repository.UserRepository;
                            import org.mrpaulwoods.application.exception.UserNotFoundException;
                            import lombok.RequiredArgsConstructor;
                            import lombok.extern.slf4j.Slf4j;
                            import org.springframework.stereotype.Service;
                            import reactor.core.publisher.Flux;
                            import reactor.core.publisher.Mono;
                            
                            @RequiredArgsConstructor
                            @Slf4j
                            @Service
                            public class UserService {
                            
                            \tprivate final UserRepository userRepository;
                            
                            \tpublic Flux<UserDto> list() {
                            \t\tlog.debug("list");
                            \t\treturn userRepository.findAll()
                            \t\t\t.map(UserMapper::toDto);
                            \t}
                            
                            \tpublic Mono<UserDto> create(UserDto dto) {
                            \t\tlog.debug("create: {}", dto);
                            \t\treturn Mono.justOrEmpty(dto)
                            \t\t\t.map(UserMapper::toEntity)
                            \t\t\t.flatMap(userRepository::save)
                            \t\t\t.map(UserMapper::toDto);
                            \t}
                            
                            \tpublic Mono<UserDto> read(Long id) {
                            \t\tlog.debug("read: {}", id);
                            \t\treturn userRepository.findById(id)
                            \t\t\t.switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                            \t\t\t.map(UserMapper::toDto);
                            \t}
                            
                            \tpublic Mono<UserDto> update(Long id, UserDto dto) {
                            \t\tlog.debug("update: {} -> {}", id, dto);
                            \t\treturn userRepository.findById(id)
                            \t\t\t.switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                            \t\t\t.map(e -> UserMapper.update(dto, e))
                            \t\t\t.flatMap(userRepository::save)
                            \t\t\t.map(UserMapper::toDto);
                            \t}
                            
                            \tpublic Mono<Void> delete(Long id) {
                            \t\tlog.debug("delete: {}", id);
                            \t\treturn userRepository.findById(id)
                            \t\t\t.switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                            \t\t\t.flatMap(u -> userRepository.delete(u));
                            \t}
                            
                            }
                            
                            """, code.getContentAsString());
                })
                .verifyComplete();
    }

    @Test
    void controller() {
        post().assertNext(gr -> {

                    Code code = gr.code(CONTROLLER);
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/controller/UserController.java", code.getFileName());
                    Assertions.assertEquals("""
                            package org.mrpaulwoods.application.controller;
                            
                            import org.mrpaulwoods.application.dto.UserDto;
                            import org.mrpaulwoods.application.service.UserService;
                            import org.springframework.http.ProblemDetail
                            import lombok.RequiredArgsConstructor;
                            import lombok.extern.slf4j.Slf4j;
                            import org.springframework.http.HttpStatus;
                            import org.springframework.web.bind.annotation.*;
                            import reactor.core.publisher.Flux;
                            import reactor.core.publisher.Mono;
                            import jakarta.validation.Valid;
                            
                            @RequiredArgsConstructor
                            @Slf4j
                            @RestController
                            @RequestMapping("/v1/user")
                            public class UserController {
                            
                            \tprivate final UserService userService;
                            
                            \t@GetMapping
                            \tpublic Flux<UserDto> list() {
                            \t\tlog.info("list");
                            \t\treturn userService.list();
                            \t}
                            
                            \t@PostMapping
                            \t@ResponseStatus(code = HttpStatus.CREATED)
                            \tpublic Mono<UserDto> create(@Valid @RequestBody UserDto dto) {
                            \t\tlog.info("create: {}", dto);
                            \t\treturn userService.create(dto);
                            \t}
                            
                            \t@GetMapping("/{id}")
                            \tpublic Mono<UserDto> read(@PathVariable Long id) {
                            \t\tlog.info("read: {}", id);
                            \t\treturn userService.read(id);
                            \t}
                            
                            \t@PutMapping("/{id}")
                            \tpublic Mono<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserDto dto) {
                            \t\tlog.info("update: {} -> {}", id, dto);
                            \t\treturn userService.update(id, dto);
                            \t}
                            
                            \t@DeleteMapping("/{id}")
                            \t@ResponseStatus(code = HttpStatus.NO_CONTENT)
                            \tpublic Mono<Void> delete(@PathVariable Long id) {
                            \t\tlog.info("delete: {}", id);
                            \t\treturn userService.delete(id);
                            \t}
                            
                            \t@ExceptionHandler(UserNotFoundException.class)
                            \tpublic ProblemDetail handleException(UserNotFoundException ex) {
                            \t\tvar problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
                            \t\tproblem.setType(URI.create("http://www.example.com/problems/user/not-found"));
                            \t\tproblem.setTitle("User Not Found");
                            \t\tproblem.setDetail(ex.getMessage());
                            \t\treturn problem;
                            \t}
                            
                            }
                            
                            """, code.getContentAsString());

                })
                .verifyComplete();
    }

}
