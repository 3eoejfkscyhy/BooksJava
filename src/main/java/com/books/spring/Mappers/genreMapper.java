package com.books.spring.Mappers;

import com.books.spring.DTOs.genreDTO;
import com.books.spring.Models.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface genreMapper {
    genreDTO toDto(Genre genre);

    @Mapping(target = "genreID", ignore = true)
    @Mapping(target = "books", ignore = true)
    Genre toEntity(genreDTO dto);
}