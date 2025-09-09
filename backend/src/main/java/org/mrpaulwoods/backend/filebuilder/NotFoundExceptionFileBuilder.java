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
        int before = code.getContentAsString().length();
        technologies.forEach(technology -> technology.importCodeBlock(appRequest, FileBuilderType.NOTFOUND_EXCEPTION, code));
        if (before != code.getContentAsString().length()) {
            code.append("\n");
        }

        // class
        code.append("public class ");
        code.append(appRequest.getNotFoundExceptionClassName());
        code.append(" extends RuntimeException {\n\n");

        // constructor
        code.append("\tpublic ");
        code.append(appRequest.getNotFoundExceptionClassName());
        code.append("(");
        code.append(appRequest.getIdFieldType());
        code.append(" id) {\n");
        code.append("\t\tsuper(\"The ");
        code.append(appRequest.getEntityObjectName());
        code.append(" was not found: \" + id);\n");
        code.append("\t}\n\n");

        // end class
        FileBuilderUtil.appendClassEnd(code);

        return code;
    }

}
