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

        code.append("import org.springframework.http.ProblemDetail\n");

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
        code.append("\t@GetMapping\n");
        code.append("\tpublic Flux<");
        code.append(appRequest.getDtoClassName());
        code.append("> list() {\n");
        code.append("\t\tlog.info(\"list\");\n");
        code.append("\t\treturn ");
        code.append(appRequest.getServiceObjectName());
        code.append(".list();\n");
        code.append("\t}\n");
        code.append("\n");

        // create
        code.append("\t@PostMapping\n");
        code.append("\t@ResponseStatus(code = HttpStatus.CREATED)\n");
        code.append("\tpublic Mono<");
        code.append(appRequest.getDtoClassName());
        code.append("> create(@Valid @RequestBody ");
        code.append(appRequest.getDtoClassName());
        code.append(" dto) {\n");
        code.append("\t\tlog.info(\"create: {}\", dto);\n");
        code.append("\t\treturn ");
        code.append(appRequest.getServiceObjectName());
        code.append(".create(dto);\n");
        code.append("\t}\n");
        code.append("\n");

        // read
        code.append("\t@GetMapping(\"/{id}\")\n");
        code.append("\tpublic Mono<");
        code.append(appRequest.getDtoClassName());
        code.append("> read(@PathVariable ");
        code.append(appRequest.getIdFieldType());
        code.append(" id) {\n");
        code.append("\t\tlog.info(\"read: {}\", id);\n");
        code.append("\t\treturn ");
        code.append(appRequest.getServiceObjectName());
        code.append(".read(id);\n");
        code.append("\t}\n");
        code.append("\n");

        // update
        code.append("\t@PutMapping(\"/{id}\")\n");
        code.append("\tpublic Mono<");
        code.append(appRequest.getDtoClassName());
        code.append("> update(@PathVariable ");
        code.append(appRequest.getIdFieldType());
        code.append(" id, @Valid @RequestBody ");
        code.append(appRequest.getDtoClassName());
        code.append(" dto) {\n");
        code.append("\t\tlog.info(\"update: {} -> {}\", id, dto);\n");
        code.append("\t\treturn ");
        code.append(appRequest.getServiceObjectName());
        code.append(".update(id, dto);\n");
        code.append("\t}\n");
        code.append("\n");

        // delete
        code.append("\t@DeleteMapping(\"/{id}\")\n");
        code.append("\t@ResponseStatus(code = HttpStatus.NO_CONTENT)\n");
        code.append("\tpublic Mono<Void> delete(@PathVariable ");
        code.append(appRequest.getIdFieldType());
        code.append(" id) {\n");
        code.append("\t\tlog.info(\"delete: {}\", id);\n");
        code.append("\t\treturn ");
        code.append(appRequest.getServiceObjectName());
        code.append(".delete(id);\n");
        code.append("\t}\n");
        code.append("\n");

        // not found exception
        code.append("\t@ExceptionHandler(");
        code.append(appRequest.getNotFoundExceptionClassName());
        code.append(".class)\n");
        code.append("\tpublic ProblemDetail handleException(");
        code.append(appRequest.getNotFoundExceptionClassName());
        code.append(" ex) {\n");
        code.append("\t\tvar problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());\n");
        code.append("\t\tproblem.setType(URI.create(\"http://www.example.com/problems/");
        code.append(appRequest.getEntityObjectName());
        code.append("/not-found\"));\n");
        code.append("\t\tproblem.setTitle(\"");
        code.append(appRequest.getEntityClassName());
        code.append(" Not Found\");\n");
        code.append("\t\tproblem.setDetail(ex.getMessage());\n");
        code.append("\t\treturn problem;\n");
        code.append("\t}\n");
        code.append("\n");

        // end class
        FileBuilderUtil.appendClassEnd(code);

        return code;
    }

}
