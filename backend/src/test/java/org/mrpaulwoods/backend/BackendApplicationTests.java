package org.mrpaulwoods.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;
import org.mrpaulwoods.backend.generate.dto.GenerateResults;
import org.mrpaulwoods.backend.utils.FileBuilderName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BackendApplicationTests {

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
                .field(Field.builder()
                        .name("createdAt")
                        .type("LocalDateTime")
                        .build())
                .build();

        webClient.post()
                .uri("/v1/generate")
                .bodyValue(appRequest)
                .retrieve()
                .bodyToMono(GenerateResults.class)
                .as(StepVerifier::create)
                .assertNext(gr -> {

                    Assertions.assertEquals(7, gr.codes().size());

                    Assertions.assertEquals(FileBuilderName.ENTITY, gr.code(0).getName());
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/entity/User.java", gr.codes().get(0).getFileName());
                    Assertions.assertEquals("""
                            package org.mrpaulwoods.application.entity;
                            
                            import java.time.LocalDateTime;
                            import lombok.*;
                            import org.springframework.data.annotation.Id;
                            import java.util.UUID;
                            
                            @Data
                            @Builder
                            @NoArgsConstructor
                            @AllArgsConstructor
                            public class User {
                            
                            \t@Id
                            \tprivate UUID id;
                            
                            \tprivate String name;
                            
                            \tprivate LocalDateTime createdAt;
                            
                            }
                            
                            """, gr.codes().get(0).getContentAsString());

                    Assertions.assertEquals(FileBuilderName.DTO, gr.code(1).getName());
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/dto/UserDto.java", gr.codes().get(1).getFileName());
                    Assertions.assertEquals("""
                            package org.mrpaulwoods.application.dto;
                            
                            import java.time.LocalDateTime;
                            import lombok.*;
                            import java.util.UUID;
                            import jakarta.validation.constraints.Size;
                            
                            @Data
                            @Builder
                            @NoArgsConstructor
                            @AllArgsConstructor
                            public class UserDto {
                            
                            \tprivate UUID id;
                            
                            \t@Size(max = 100)
                            \tprivate String name;
                            
                            \tprivate LocalDateTime createdAt;
                            
                            }
                            
                            """, gr.codes().get(1).getContentAsString());

                    Assertions.assertEquals(FileBuilderName.MAPPER, gr.code(2).getName());
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/mapper/UserMapper.java", gr.codes().get(2).getFileName());
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
                            \t\t\t.createdAt(entity.getCreatedAt())
                            \t\t\t.build();
                            \t}
                            
                            \tpublic static User toEntity(UserDto dto) {
                            \t\tif (dto == null) {
                            \t\t\treturn null;
                            \t\t}
                            \t\treturn User.builder()
                            \t\t\t.id(dto.getId())
                            \t\t\t.name(dto.getName())
                            \t\t\t.createdAt(dto.getCreatedAt())
                            \t\t\t.build();
                            \t}
                            
                            \tpublic static User update(UserDto dto, User entity) {
                            \t\tif (dto == null || entity == null) {
                            \t\t\treturn null;
                            \t\t}
                            \t\tentity.setName(dto.getName());
                            \t\tentity.setCreatedAt(dto.getCreatedAt());
                            \t\treturn entity;
                            \t}
                            
                            }
                            
                            """, gr.codes().get(2).getContentAsString());

                    Assertions.assertEquals(FileBuilderName.REPOSITORY, gr.code(3).getName());
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/repository/UserRepository.java", gr.codes().get(3).getFileName());
                    Assertions.assertEquals("""
                            package org.mrpaulwoods.application.repository;
                            
                            import org.mrpaulwoods.application.entity.User;
                            import org.springframework.data.repository.reactive.ReactiveCrudRepository;
                            import java.util.UUID;
                            
                            public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
                            }
                            
                            """, gr.codes().get(3).getContentAsString());

                    Assertions.assertEquals(FileBuilderName.NOTFOUND, gr.code(4).getName());
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/exception/UserNotFoundException.java", gr.codes().get(4).getFileName());
                    Assertions.assertEquals("""
                            package org.mrpaulwoods.application.exception;
                            
                            import java.util.UUID;
                            
                            public class UserNotFoundException extends RuntimeException {
                            
                            \tpublic UserNotFoundException(UUID id) {
                            \t\tsuper("The user was not found: " + id);
                            \t}
                            
                            }
                            
                            """, gr.codes().get(4).getContentAsString());

                    Assertions.assertEquals(FileBuilderName.SERVICE, gr.code(5).getName());
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/service/UserService.java", gr.codes().get(5).getFileName());
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
                            import java.util.UUID;
                            
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
                            
                            \tpublic Mono<UserDto> read(UUID id) {
                            \t\tlog.debug("read: {}", id);
                            \t\treturn userRepository.findById(id)
                            \t\t\t.switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                            \t\t\t.map(UserMapper::toDto);
                            \t}
                            
                            \tpublic Mono<UserDto> update(UUID id, UserDto dto) {
                            \t\tlog.debug("update: {} -> {}", id, dto);
                            \t\treturn userRepository.findById(id)
                            \t\t\t.switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                            \t\t\t.map(e -> UserMapper.update(dto, e))
                            \t\t\t.flatMap(userRepository::save)
                            \t\t\t.map(UserMapper::toDto);
                            \t}
                            
                            \tpublic Mono<Void> delete(UUID id) {
                            \t\tlog.debug("delete: {}", id);
                            \t\treturn userRepository.findById(id)
                            \t\t\t.switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                            \t\t\t.flatMap(u -> userRepository.delete(u));
                            \t}
                            
                            }
                            
                            """, gr.codes().get(5).getContentAsString());

                    Assertions.assertEquals(FileBuilderName.CONTROLLER, gr.code(6).getName());
                    Assertions.assertEquals("src/main/java/org/mrpaulwoods/application/controller/UserController.java", gr.codes().get(6).getFileName());
                    Assertions.assertEquals("""
                            package org.mrpaulwoods.application.controller;
                            
                            import org.mrpaulwoods.application.dto.UserDto;
                            import org.mrpaulwoods.application.service.UserService;
                            import lombok.RequiredArgsConstructor;
                            import lombok.extern.slf4j.Slf4j;
                            import org.springframework.http.HttpStatus;
                            import org.springframework.web.bind.annotation.*;
                            import reactor.core.publisher.Flux;
                            import reactor.core.publisher.Mono;
                            import java.util.UUID;
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
                            \tpublic Mono<UserDto> read(@PathVariable UUID id) {
                            \t\tlog.info("read: {}", id);
                            \t\treturn userService.read(id);
                            \t}
                            
                            \t@PutMapping("/{id}")
                            \tpublic Mono<UserDto> update(@PathVariable UUID id, @Valid @RequestBody UserDto dto) {
                            \t\tlog.info("update: {} -> {}", id, dto);
                            \t\treturn userService.update(id, dto);
                            \t}
                            
                            \t@DeleteMapping("/{id}")
                            \t@ResponseStatus(code = HttpStatus.NO_CONTENT)
                            \tpublic Mono<Void> delete(@PathVariable UUID id) {
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
                            
                            """, gr.codes().get(6).getContentAsString());

                })
                .verifyComplete();
    }

}
