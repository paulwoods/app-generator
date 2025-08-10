package org.mrpaulwoods.backend.technology;

import org.mrpaulwoods.backend.generate.dto.AppRequest;
import org.springframework.stereotype.Component;

@Component
public class SpringDataTechnology implements Technology {

    @Override
    public void importCodeBlock(AppRequest appRequest, StringBuilder sb) {
        sb.append("import org.springframework.data.annotation.Id;\n");
    }
    
}
