package org.mrpaulwoods.sample1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.sample1.dto.UserDto;
import org.mrpaulwoods.sample1.exception.UserNotFoundException;
import org.mrpaulwoods.sample1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<UserDto> list() {
        log.info("list");
        return userService.list();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<UserDto> create(@Valid @RequestBody UserDto dto) {
        log.info("create: {}", dto);
        return userService.create(dto);
    }

    @GetMapping("/{id}")
    public Mono<UserDto> read(@PathVariable UUID id) {
        log.info("read: {}", id);
        return userService.read(id);
    }

    @PutMapping("/{id}")
    public Mono<UserDto> update(@PathVariable UUID id, @Valid @RequestBody UserDto dto) {
        log.info("update: {} -> {}", id, dto);
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id) {
        log.info("delete: {}", id);
        return userService.delete(id);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleException(UserNotFoundException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setType(URI.create("http://www.example.com/problems/job/not-found"));
        problem.setTitle("User Not Found");
        problem.setDetail(ex.getMessage());
        return problem;
    }

}
