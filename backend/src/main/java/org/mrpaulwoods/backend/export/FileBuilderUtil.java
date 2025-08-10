package org.mrpaulwoods.backend.export;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;

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
        code.append(".entity;\n\n");
    }

}
