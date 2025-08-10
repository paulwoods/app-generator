package org.mrpaulwoods.backend.export;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.technology.Technology;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileBuilderUtil {

    public static void buildFileName(String prefix, String stub, String suffix, AppRequest appRequest, Code code) {
        List<String> elements = new ArrayList<>();
        elements.add(prefix);
        elements.add("main");
        elements.add("java");
        elements.addAll(Arrays.asList(appRequest.getPkg().split("\\.")));
        elements.add(stub);
        elements.add(appRequest.getClassName() + suffix + ".java");
        Path p = Path.of("", elements.toArray(new String[0]));
        code.setFileName(p.toString());
    }

    public static void appendPackage(String entity, AppRequest appRequest, Code code) {
        code.append("package ");
        code.append(appRequest.getPkg());
        code.append(".");
        code.append(entity);
        code.append(";\n\n");
    }

    public static void appendImports(List<Technology> technologies, FileBuilderType type, AppRequest appRequest, Code code) {
        technologies.forEach(t -> t.importCodeBlock(appRequest, type, code));
        code.append("\n");
    }

    public static void appendClassAnnotations(List<Technology> technologies, FileBuilderType type, AppRequest appRequest, Code code) {
        technologies.forEach(t -> t.classAnnotationsCodeBlock(appRequest, type, code));
    }
}
