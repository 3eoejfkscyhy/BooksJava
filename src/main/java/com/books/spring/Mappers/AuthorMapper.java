package com.books.spring.Mappers;

import com.books.spring.DTOs.AuthorDTO;
import com.books.spring.Models.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AuthorMapper {
    AuthorDTO toDto(Author author);

    @Mapping(target = "authorID", ignore = true)
    Author toEntity(AuthorDTO dto);
}