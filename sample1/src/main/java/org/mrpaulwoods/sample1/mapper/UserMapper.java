package org.mrpaulwoods.sample1.mapper;

import org.mrpaulwoods.sample1.dto.UserDto;
import org.mrpaulwoods.sample1.entity.User;

public class UserMapper {

    public static UserDto toDto(User entity) {
        if (entity == null) {
            return null;
        }
        return UserDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }

    public static User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        return User.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }

    public static User update(UserDto dto, User entity) {
        if (dto == null || entity == null) {
            return null;
        }
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        return entity;
    }

}
