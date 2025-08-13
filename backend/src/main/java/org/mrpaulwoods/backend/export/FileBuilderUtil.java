package org.mrpaulwoods.backend.export;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;
import org.mrpaulwoods.backend.technology.Technology;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileBuilderUtil {

    public static void buildFileName(String folder, String stub, String name, String suffix, AppRequest appRequest, Code code) {
        List<String> elements = new ArrayList<>();
        elements.add("src");
        elements.add(folder);
        elements.add("java");
        elements.addAll(Arrays.asList(appRequest.getPkg().split("\\.")));
        elements.add(stub);
        elements.add(name + suffix + ".java");
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

    public static void appendClass(String name, String suffix, Code code) {
        code.append("public class ");
        code.append(name);
        code.append(suffix);
        code.append(" {\n\n");
    }

    public static void appendFieldAnnotations(List<Technology> technologies, FileBuilderType type, AppRequest appRequest, Field field, Code code) {
        technologies.forEach(t -> t.fieldAnnotationCodeBlock(appRequest, type, field, code));
    }

    public static void appendField(String type, String name, Code code) {
        code.append("\tprivate ");
        code.append(type);
        code.append(" ");
        code.append(name);
        code.append(";\n\n");
    }

    public static void appendClassEnd(Code code) {
        code.append("}\n\n");
    }

}
