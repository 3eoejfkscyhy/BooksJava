package com.books.spring.Mappers;

import com.books.spring.DTOs.UserDTO;
import com.books.spring.Models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    @Mapping(target = "userID", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "collections", ignore = true)
    @Mapping(target = "UserSide", ignore = true)
    User toEntity(UserDTO dto);

}