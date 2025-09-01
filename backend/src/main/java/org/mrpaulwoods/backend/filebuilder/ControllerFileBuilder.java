package org.mrpaulwoods.backend.filebuilder;

import lombok.RequiredArgsConstructor;
import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.technology.Technology;
import org.mrpaulwoods.backend.types.FileBuilderType;
import org.mrpaulwoods.backend.utils.FileBuilderName;
import org.mrpaulwoods.backend.utils.FileBuilderUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public final class ControllerFileBuilder implements FileBuilder {

    private final List<Technology> technologies;

    @Override
    public FileBuilderName getName() {
        return FileBuilderName.CONTROLLER;
    }

    @Override
    public Code build(AppRequest appRequest) {

        Code code = Code.builder().name(getName()).build();

        // filename
        code.setFileName(FileBuilderUtil.createSourceFilename(FileBuilderUtil.absoluteController(appRequest)));

        // package
        FileBuilderUtil.appendPackage("controller", appRequest, code);

        // imports
        code.append("import ");
        code.append(FileBuilderUtil.absoluteDto(appRequest));
        code.append(";\n");

        code.append("import ");
        code.append(FileBuilderUtil.absoluteService(appRequest));
        code.append(";\n");

        technologies.forEach(technology -> technology.importCodeBlock(appRequest, FileBuilderType.CONTROLLER, code));
        code.append("\n");

        // class annotations
        technologies.forEach(technology -> technology.classAnnotationsCodeBlock(appRequest, FileBuilderType.CONTROLLER, code));

        // class
        code.append("public class ");
        code.append(appRequest.getControllerClassName());
        code.append(" {\n\n");

        // beans
        code.append("\tprivate final ");
        code.append(appRequest.getServiceClassName());
        code.append(" ");
        code.append(appRequest.getServiceObjectName());
        code.append(";\n\n");

        // list
        code.append("""
                \t@GetMapping
                \tpublic Flux<%s> list() {
                \t\tlog.info("list");
                \t\treturn %s.list();
                \t}
                
                """.formatted(
                appRequest.getDtoClassName(),
                appRequest.getServiceObjectName()
        ));

        // create
        code.append("""
                \t@PostMapping
                \t@ResponseStatus(code = HttpStatus.CREATED)
                \tpublic Mono<%s> create(@Valid @RequestBody %s dto) {
                \t\tlog.info("create: {}", dto);
                \t\treturn %s.create(dto);
                \t}
                
                """.formatted(
                appRequest.getDtoClassName(),
                appRequest.getDtoClassName(),
                appRequest.getServiceObjectName()
        ));

        // read
        code.append("""
                \t@GetMapping("/{id}")
                \tpublic Mono<%s> read(@PathVariable UUID id) {
                \t\tlog.info("read: {}", id);
                \t\treturn %s.read(id);
                \t}
                
                """.formatted(
                appRequest.getDtoClassName(),
                appRequest.getServiceObjectName()
        ));

        // update
        code.append("""
                \t@PutMapping("/{id}")
                \tpublic Mono<%s> update(@PathVariable UUID id, @Valid @RequestBody %s dto) {
                \t\tlog.info("update: {} -> {}", id, dto);
                \t\treturn %s.update(id, dto);
                \t}
                
                """.formatted(
                appRequest.getDtoClassName(),
                appRequest.getDtoClassName(),
                appRequest.getServiceObjectName()
        ));

        // delete
        code.append("""
                \t@DeleteMapping("/{id}")
                \t@ResponseStatus(code = HttpStatus.NO_CONTENT)
                \tpublic Mono<Void> delete(@PathVariable UUID id) {
                \t\tlog.info("delete: {}", id);
                \t\treturn %s.delete(id);
                \t}
                
                """.formatted(
                appRequest.getServiceObjectName()
        ));

        // not found exception
        code.append("""
                \t@ExceptionHandler(%s.class)
                \tpublic ProblemDetail handleException(%s ex) {
                \t\tvar problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
                \t\tproblem.setType(URI.create("http://www.example.com/problems/%s/not-found"));
                \t\tproblem.setTitle("%s Not Found");
                \t\tproblem.setDetail(ex.getMessage());
                \t\treturn problem;
                \t}
                
                """.formatted(
                appRequest.getNotFoundExceptionClassName(),
                appRequest.getNotFoundExceptionClassName(),
                appRequest.getEntityObjectName(),
                appRequest.getEntityClassName()
        ));

        // end class
        FileBuilderUtil.appendClassEnd(code);

        return code;
    }

}
