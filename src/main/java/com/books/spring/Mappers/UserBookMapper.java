package com.books.spring.Mappers;

import com.books.spring.DTOs.UserBookDTO;
import com.books.spring.Models.UserBooks;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserBookMapper {
    @Mapping(target = "userID", source = "user.userID")
    @Mapping(target = "isbn", source = "book.isbn")
    UserBookDTO toDto(UserBooks userBook);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    UserBooks toEntity(UserBookDTO dto);
}