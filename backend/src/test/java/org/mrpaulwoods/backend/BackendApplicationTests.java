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
            
            //file: src/main/java/org/mrpaulwoods/application/mapper/UserMapper.java
            
            package org.mrpaulwoods.application.mapper;
            
            import org.mrpaulwoods.application.dto.UserDto;
            import org.mrpaulwoods.application.entity.User;
            
            public class UserMapper {
            
            	public static UserDto toDto(User entity) {
            		if (entity == null) {
            			return null;
            		}
            		return UserDto.builder()
            			.id(entity.getId())
            			.name(entity.getName())
            			.build();
            	}
            
            	public static User toEntity(UserDto dto) {
            		if (dto == null) {
            			return null;
            		}
            		return User.builder()
            			.id(dto.getId())
            			.name(dto.getName())
            			.build();
            	}
            
            	public static User update(UserDto dto, User entity) {
            		if (dto == null || entity == null) {
            			return null;
            		}
            		entity.setName(dto.getName());
            		return entity;
            	}
            
            }
            
            //file: src/main/java/org/mrpaulwoods/application/repository/UserRepository.java
            
            package org.mrpaulwoods.application.repository;
            
            import org.mrpaulwoods.application.entity.User;
            import org.springframework.data.repository.reactive.ReactiveCrudRepository;
            import java.util.UUID;
            
            public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
            }
            
            //file: src/main/java/org/mrpaulwoods/application/service/UserService.java
            
            package org.mrpaulwoods.application.service;
            
            import org.mrpaulwoods.application.dto.UserDto;
            import org.mrpaulwoods.application.mapper.UserMapper;
            import org.mrpaulwoods.application.repository.UserRepository;
            import org.mrpaulwoods.sample1.exception.UserNotFoundException;
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
            
            	private final UserRepository userRepository;
            
            	public Flux<UserDto> list() {
            		log.debug("list");
            		return userRepository.findAll()
            			.map(UserMapper::toDto);
            	}
            
            	public Mono<UserDto> create(UserDto dto) {
            		log.debug("create: {}", dto);
            		return Mono.justOrEmpty(dto)
            			.map(UserMapper::toEntity)
            			.flatMap(userRepository::save)
            			.map(UserMapper::toDto);
            	}
            
            	public Mono<UserDto> read(UUID id) {
            		log.debug("read: {}", id);
            		return userRepository.findById(id)
            			.switchIfEmpty(Mono.error(new UserNotFoundException(id)))
            			.map(UserMapper::toDto);
            	}
            
            	public Mono<UserDto> update(UUID id, UserDto dto) {
            		log.debug("update: {} -> {}", id, dto);
            		return userRepository.findById(id)
            			.switchIfEmpty(Mono.error(new UserNotFoundException(id)))
            			.map(e -> UserMapper.update(dto, e))
            			.flatMap(userRepository::save)
            			.map(UserMapper::toDto);
            	}
            
            	public Mono<Void> delete(UUID id) {
            		log.debug("delete: {}", id);
            		return userRepository.findById(id)
            			.switchIfEmpty(Mono.error(new UserNotFoundException(id)))
            			.flatMap(u -> userRepository.delete(u));
            	}
            
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
