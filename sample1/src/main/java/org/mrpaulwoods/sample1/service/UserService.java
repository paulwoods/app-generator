package org.mrpaulwoods.sample1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrpaulwoods.sample1.dto.UserDto;
import org.mrpaulwoods.sample1.exception.UserNotFoundException;
import org.mrpaulwoods.sample1.mapper.UserMapper;
import org.mrpaulwoods.sample1.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
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
                .flatMap(u -> userRepository.deleteById(id));
    }

}
