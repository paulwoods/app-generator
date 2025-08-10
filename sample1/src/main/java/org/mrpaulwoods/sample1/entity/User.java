package org.mrpaulwoods.sample1.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    private UUID id;

    private String firstName;

    private String lastName;

}
