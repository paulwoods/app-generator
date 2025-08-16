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
@Order(FileBuilder.REPOSITORY_ORDER)
@RequiredArgsConstructor
public final class RepositoryFileBuilder implements FileBuilder {

    private final List<Technology> technologies;

    @Override
    public void build(AppRequest appRequest, Code code) {
        // filename
        code.setFileName(FileBuilderUtil.createSourceFilename(FileBuilderUtil.absoluteRepository(appRequest)));

        // package
        FileBuilderUtil.appendPackage("repository", appRequest, code);

        // imports
        code.append("import ");
        code.append(appRequest.getPkg());
        code.append(".entity.");
        code.append(appRequest.getEntityClassName());
        code.append(";\n");

        technologies.forEach(technology -> technology.importCodeBlock(appRequest, FileBuilderType.REPOSITORY, code));
        code.append("\n");

        // type
        code.append("public interface ");
        code.append(appRequest.getRepositoryClassName());
        code.append(" extends ReactiveCrudRepository<");
        code.append(appRequest.getEntityClassName());
        code.append(", UUID> {\n");
        code.append("}\n\n");
    }

}
