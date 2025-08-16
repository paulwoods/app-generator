package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.Code;
import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.mrpaulwoods.backend.generate.dto.Field;
import org.mrpaulwoods.backend.types.FileBuilderType;
import org.springframework.stereotype.Component;

@Component
public final class SpringDataTechnology implements Technology {

    @Override
    public void importCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {

        switch (type) {
            case ENTITY -> {
                if (appRequest.getFields().stream().anyMatch(Field::isId)) {
                    code.append("import org.springframework.data.annotation.Id;\n");
                }
            }
            case REPOSITORY ->
                    code.append("import org.springframework.data.repository.reactive.ReactiveCrudRepository;\n");
            case SERVICE -> {
                code.append("import org.springframework.stereotype.Service;\n");
                code.append("import reactor.core.publisher.Flux;\n");
                code.append("import reactor.core.publisher.Mono;\n");
            }
        }

    }

    @Override
    public void classAnnotationsCodeBlock(AppRequest appRequest, FileBuilderType type, Code code) {
        if (FileBuilderType.SERVICE == type) {
            code.append("@Service\n");
        }
    }

    @Override
    public void fieldAnnotationCodeBlock(AppRequest appRequest, FileBuilderType type, Field field, Code code) {

        if (FileBuilderType.ENTITY == type) {
            if (field.isId()) {
                code.append("\t@Id\n");
            }
        }

    }

}
