package com.books.spring.Mappers;

import com.books.spring.DTOs.userDTO;
import com.books.spring.Models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface userMapper {
    userDTO toDto(User user);

    @Mapping(target = "userID", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "collections", ignore = true)
    @Mapping(target = "UserSide", ignore = true)
    User toEntity(userDTO dto);

}