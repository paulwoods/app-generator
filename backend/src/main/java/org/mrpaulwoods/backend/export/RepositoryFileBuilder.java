package org.mrpaulwoods.backend.export;

import lombok.RequiredArgsConstructor;
import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.technology.Technology;
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
        FileBuilderUtil.buildFileName("src", "repository", appRequest.getEntity(), "Repository", appRequest, code);

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
        code.append(appRequest.getEntity());
        code.append("Repository extends ReactiveCrudRepository<");
        code.append(appRequest.getEntityClassName());
        code.append(", UUID> {\n");
        code.append("}\n\n");
    }

}
/*
package org.mrpaulwoods.sample1.repository;

import org.mrpaulwoods.sample1.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
}

 */