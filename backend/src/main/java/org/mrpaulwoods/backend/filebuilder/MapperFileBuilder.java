package org.mrpaulwoods.backend.filebuilder;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.utils.Constants;
import org.mrpaulwoods.backend.utils.FileBuilderUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(FileBuilder.MAPPER_ORDER)
public final class MapperFileBuilder implements FileBuilder {

    @Override
    public void build(AppRequest appRequest, Code code) {
        // filename
        code.setFileName(FileBuilderUtil.createSourceFilename(FileBuilderUtil.absoluteMapper(appRequest)));

        // package
        FileBuilderUtil.appendPackage("mapper", appRequest, code);

        // imports
        code.append("import ");
        code.append(appRequest.getPkg());
        code.append(".dto.");
        code.append(appRequest.getDtoClassName());
        code.append(";\n");

        code.append("import ");
        code.append(appRequest.getPkg());
        code.append(".entity.");
        code.append(appRequest.getEntityClassName());
        code.append(";\n\n");

        // class annotations

        // class
        FileBuilderUtil.appendClass(appRequest.getEntityClassName(), Constants.MAPPER_SUFFIX, code);

        // methods
        code.append("\tpublic static ");
        code.append(appRequest.getDtoClassName());
        code.append(" toDto(");
        code.append(appRequest.getEntityClassName());
        code.append(" entity) {\n");
        code.append("\t\tif (entity == null) {\n");
        code.append("\t\t\treturn null;\n");
        code.append("\t\t}\n");

        code.append("\t\treturn ");
        code.append(appRequest.getDtoClassName());
        code.append(".builder()\n");

        appRequest.stream().forEach(field -> {
            code.append("\t\t\t.");
            code.append(field.getName());
            code.append("(entity.");
            code.append(field.getGetterName());
            code.append("())\n");
        });

        code.append("\t\t\t.build();\n");
        code.append("\t}\n\n");

        code.append("\tpublic static ");
        code.append(appRequest.getEntityClassName());
        code.append(" toEntity(");
        code.append(appRequest.getDtoClassName());
        code.append(" dto) {\n");
        code.append("\t\tif (dto == null) {\n");
        code.append("\t\t\treturn null;\n");
        code.append("\t\t}\n");

        code.append("\t\treturn ");
        code.append(appRequest.getEntityClassName());
        code.append(".builder()\n");

        appRequest.stream().forEach(field -> {
            code.append("\t\t\t.");
            code.append(field.getName());
            code.append("(dto.");
            code.append(field.getGetterName());
            code.append("())\n");
        });

        code.append("\t\t\t.build();\n");
        code.append("\t}\n\n");

        code.append("\tpublic static ");
        code.append(appRequest.getEntityClassName());
        code.append(" update(");
        code.append(appRequest.getDtoClassName());
        code.append(" dto, ");
        code.append(appRequest.getEntityClassName());
        code.append(" entity) {\n");


        code.append("\t\tif (dto == null || entity == null) {\n");
        code.append("\t\t\treturn null;\n");
        code.append("\t\t}\n");

        appRequest.stream()
                .filter(f -> !f.isId())
                .forEach(field -> {
                    code.append("\t\tentity.");
                    code.append(field.getSetterName());
                    code.append("(dto.");
                    code.append(field.getGetterName());
                    code.append("());\n");
                });

        code.append("\t\treturn entity;\n");
        code.append("\t}\n\n");

        // end class
        FileBuilderUtil.appendClassEnd(code);
    }

}
