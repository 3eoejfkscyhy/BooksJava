package com.books.spring.Mappers;

import com.books.spring.DTOs.authorDTO;
import com.books.spring.Models.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface authorMapper {
    authorDTO toDto(Author author);

    @Mapping(target = "authorID", ignore = true)
    Author toEntity(authorDTO dto);
}