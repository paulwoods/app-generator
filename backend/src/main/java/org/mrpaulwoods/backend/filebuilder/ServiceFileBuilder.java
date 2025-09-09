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
public final class ServiceFileBuilder implements FileBuilder {

    private final List<Technology> technologies;

    @Override
    public FileBuilderName getName() {
        return FileBuilderName.SERVICE;
    }

    @Override
    public Code build(AppRequest appRequest) {

        Code code = Code.builder().name(getName()).build();

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

        code.append("import ");
        code.append(FileBuilderUtil.absoluteNotFound(appRequest));
        code.append(";\n");

        technologies.forEach(technology -> technology.importCodeBlock(appRequest, FileBuilderType.SERVICE, code));
        code.append("\n");

        // class annotations
        technologies.forEach(technology -> technology.classAnnotationsCodeBlock(appRequest, FileBuilderType.SERVICE, code));

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
        code.append("\tpublic Flux<");
        code.append(appRequest.getDtoClassName());
        code.append("> list() {\n");
        code.append("\t\tlog.debug(\"list\");\n");
        code.append("\t\treturn ");
        code.append(appRequest.getRepositoryObjectName());
        code.append(".findAll()\n");
        code.append("\t\t\t.map(");
        code.append(appRequest.getMapperClassName());
        code.append("::toDto);\n");
        code.append("\t}\n");
        code.append("\n");

        // create
        code.append("\tpublic Mono<");
        code.append(appRequest.getDtoClassName());
        code.append("> create(");
        code.append(appRequest.getDtoClassName());
        code.append(" dto) {\n");
        code.append("\t\tlog.debug(\"create: {}\", dto);\n");
        code.append("\t\treturn Mono.justOrEmpty(dto)\n");
        code.append("\t\t\t.map(");
        code.append(appRequest.getMapperClassName());
        code.append("::toEntity)\n");
        code.append("\t\t\t.flatMap(");
        code.append(appRequest.getRepositoryObjectName());
        code.append("::save)\n");
        code.append("\t\t\t.map(");
        code.append(appRequest.getMapperClassName());
        code.append("::toDto);\n");
        code.append("\t}\n");
        code.append("\n");

        // read
        code.append("\tpublic Mono<");
        code.append(appRequest.getDtoClassName());
        code.append("> read(");
        code.append(appRequest.getIdFieldType());
        code.append(" id) {\n");
        code.append("\t\tlog.debug(\"read: {}\", id);\n");
        code.append("\t\treturn ");
        code.append(appRequest.getRepositoryObjectName());
        code.append(".findById(id)\n");
        code.append("\t\t\t.switchIfEmpty(Mono.error(new ");
        code.append(appRequest.getNotFoundExceptionClassName());
        code.append("(id)))\n");
        code.append("\t\t\t.map(");
        code.append(appRequest.getMapperClassName());
        code.append("::toDto);\n");
        code.append("\t}\n");
        code.append("\n");

        // update
        code.append("\tpublic Mono<");
        code.append(appRequest.getDtoClassName());
        code.append("> update(");
        code.append(appRequest.getIdFieldType());
        code.append(" id, ");
        code.append(appRequest.getDtoClassName());
        code.append(" dto) {\n");
        code.append("\t\tlog.debug(\"update: {} -> {}\", id, dto);\n");
        code.append("\t\treturn ");
        code.append(appRequest.getRepositoryObjectName());
        code.append(".findById(id)\n");
        code.append("\t\t\t.switchIfEmpty(Mono.error(new ");
        code.append(appRequest.getNotFoundExceptionClassName());
        code.append("(id)))\n");
        code.append("\t\t\t.map(e -> ");
        code.append(appRequest.getMapperClassName());
        code.append(".update(dto, e))\n");
        code.append("\t\t\t.flatMap(");
        code.append(appRequest.getRepositoryObjectName());
        code.append("::save)\n");
        code.append("\t\t\t.map(");
        code.append(appRequest.getMapperClassName());
        code.append("::toDto);\n");
        code.append("\t}\n");
        code.append("\n");

        // delete
        code.append("\tpublic Mono<Void> delete(");
        code.append(appRequest.getIdFieldType());
        code.append(" id) {\n");
        code.append("\t\tlog.debug(\"delete: {}\", id);\n");
        code.append("\t\treturn ");
        code.append(appRequest.getRepositoryObjectName());
        code.append(".findById(id)\n");
        code.append("\t\t\t.switchIfEmpty(Mono.error(new ");
        code.append(appRequest.getNotFoundExceptionClassName());
        code.append("(id)))\n");
        code.append("\t\t\t.flatMap(u -> ");
        code.append(appRequest.getRepositoryObjectName());
        code.append(".delete(u));\n");
        code.append("\t}\n");
        code.append("\n");

        // end class
        FileBuilderUtil.appendClassEnd(code);

        return code;
    }

}
