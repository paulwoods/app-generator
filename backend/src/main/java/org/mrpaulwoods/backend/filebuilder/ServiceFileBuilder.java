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
        code.setFileName(FileBuilderUtil.createSourceFilename(FileBuilderUtil.absoluteService(appRequest)));

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
        code.append("import org.mrpaulwoods.sample1.exception.UserNotFoundException;\n");

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

        // list
        code.append("""
                \tpublic Flux<%s> list() {
                \t\tlog.debug("list");
                \t\treturn %s.findAll()
                \t\t\t.map(%s::toDto);
                \t}
                
                """.formatted(
                appRequest.getDtoClassName(),
                appRequest.getRepositoryObjectName(),
                appRequest.getMapperClassName()
        ));

        // create
        code.append("""
                \tpublic Mono<%s> create(%s dto) {
                \t\tlog.debug("create: {}", dto);
                \t\treturn Mono.justOrEmpty(dto)
                \t\t\t.map(%s::toEntity)
                \t\t\t.flatMap(%s::save)
                \t\t\t.map(%s::toDto);
                \t}
                
                """.formatted(
                appRequest.getDtoClassName(),
                appRequest.getDtoClassName(),
                appRequest.getMapperClassName(),
                appRequest.getRepositoryObjectName(),
                appRequest.getMapperClassName()
        ));

        // read
        code.append("""
                \tpublic Mono<%s> read(UUID id) {
                \t\tlog.debug("read: {}", id);
                \t\treturn %s.findById(id)
                \t\t\t.switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                \t\t\t.map(%s::toDto);
                \t}
                
                """.formatted(
                appRequest.getDtoClassName(),
                appRequest.getRepositoryObjectName(),
                appRequest.getMapperClassName()
        ));

        // update
        code.append("""
                \tpublic Mono<%s> update(UUID id, %s dto) {
                \t\tlog.debug("update: {} -> {}", id, dto);
                \t\treturn %s.findById(id)
                \t\t\t.switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                \t\t\t.map(e -> %s.update(dto, e))
                \t\t\t.flatMap(%s::save)
                \t\t\t.map(%s::toDto);
                \t}
                
                """.formatted(
                appRequest.getDtoClassName(),
                appRequest.getDtoClassName(),
                appRequest.getRepositoryObjectName(),
                appRequest.getMapperClassName(),
                appRequest.getRepositoryObjectName(),
                appRequest.getMapperClassName()
        ));

        // delete
        code.append("""
                \tpublic Mono<Void> delete(UUID id) {
                \t\tlog.debug("delete: {}", id);
                \t\treturn %s.findById(id)
                \t\t\t.switchIfEmpty(Mono.error(new UserNotFoundException(id)))
                \t\t\t.flatMap(u -> %s.delete(u));
                \t}
                
                """.formatted(
                appRequest.getRepositoryObjectName(),
                appRequest.getRepositoryObjectName()
        ));

        // end class
        FileBuilderUtil.appendClassEnd(code);
    }
}
