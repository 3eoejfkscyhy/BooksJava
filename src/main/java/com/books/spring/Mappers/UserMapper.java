package com.books.spring.Mappers;

import com.books.spring.DTOs.AuthorDTO;
import com.books.spring.DTOs.UserDTO;
import com.books.spring.Models.Author;
import com.books.spring.Models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    @Mapping(target = "userID", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "collections", ignore = true)
    @Mapping(target = "UserSide", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    User toEntity(UserDTO dto);

    @Named("activeUsers")
    default Set<UserDTO> toActiveDtos(Set<User> users) {
        return users.stream()
                .filter(user-> !user.isDeleted())
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

}