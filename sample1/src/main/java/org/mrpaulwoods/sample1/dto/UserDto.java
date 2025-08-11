package org.mrpaulwoods.sample1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;

    @NotBlank
    @Size(max = 100, message = "First name must be less than 100 characters")
    private String firstName;

    @NotBlank
    @Size(max = 100, message = "Last name must be less than 100 characters")
    private String lastName;
}
