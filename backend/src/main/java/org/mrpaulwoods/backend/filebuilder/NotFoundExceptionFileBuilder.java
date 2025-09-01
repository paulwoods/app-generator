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
public final class NotFoundExceptionFileBuilder implements FileBuilder {

    private final List<Technology> technologies;

    @Override
    public FileBuilderName getName() {
        return FileBuilderName.NOTFOUND;
    }

    @Override
    public Code build(AppRequest appRequest) {

        Code code = Code.builder().name(getName()).build();

        // filename
        code.setFileName(FileBuilderUtil.createSourceFilename(FileBuilderUtil.absoluteNotFound(appRequest)));

        // package
        FileBuilderUtil.appendPackage("exception", appRequest, code);

        // imports
        technologies.forEach(technology -> technology.importCodeBlock(appRequest, FileBuilderType.NOTFOUND_EXCEPTION, code));
        code.append("\n");

        // class
        code.append("public class ");
        code.append(appRequest.getNotFoundExceptionClassName());
        code.append(" extends RuntimeException {\n\n");

        // constructor
        code.append("""
                \tpublic %s(UUID id) {
                \t\tsuper("The %s was not found: " + id);
                \t}
                
                """.formatted(
                appRequest.getNotFoundExceptionClassName(),
                appRequest.getEntityObjectName()
        ));

        // end class
        FileBuilderUtil.appendClassEnd(code);

        return code;
    }

}
