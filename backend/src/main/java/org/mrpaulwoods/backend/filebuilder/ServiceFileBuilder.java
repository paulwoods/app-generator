package org.mrpaulwoods.backend.filebuilder;

import lombok.RequiredArgsConstructor;
import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.technology.Technology;
import org.mrpaulwoods.backend.types.FileBuilderType;
import org.mrpaulwoods.backend.utils.FileBuilderUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(FileBuilder.SERVICE_ORDER)
@RequiredArgsConstructor
public final class ServiceFileBuilder implements FileBuilder {

    private final List<Technology> technologies;

    @Override
    public void build(AppRequest appRequest, Code code) {
        // filename
        FileBuilderUtil.buildFileName("src", "service", appRequest.getEntity(), "Service", appRequest, code);

        // package
        FileBuilderUtil.appendPackage("service", appRequest, code);

        // imports
        code.append("import ");
        code.append(FileBuilderUtil.absoluteDto(appRequest));
        code.append(";\n");

        code.append("import ");
        code.append(FileBuilderUtil.absoluteMapper(appRequest));
        code.append(";\n");

        code.append("import ");
        code.append(FileBuilderUtil.absoluteRepository(appRequest));
        code.append(";\n");

        //todo: import org.mrpaulwoods.sample1.exception.UserNotFoundException;

        technologies.forEach(technology -> technology.importCodeBlock(appRequest, FileBuilderType.SERVICE, code));
        code.append("\n");

        // class annotations
        technologies.forEach(technology -> technology.classAnnotationsCodeBlock(appRequest, FileBuilderType.SERVICE, code));
        code.append("\n");

        // class
        code.append("public class ");
        code.append(appRequest.getServiceClassName());
        code.append(" {\n\n");

        // beans
        code.append("\tprivate final ");
        code.append(appRequest.getRepositoryClassName());
        code.append(" ");
        code.append(appRequest.getRepositoryObjectName());
        code.append(";\n\n");


        // end class
        FileBuilderUtil.appendClassEnd(code);
    }
}

/*

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

 */