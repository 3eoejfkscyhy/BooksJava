package com.books.spring.Mappers;

import com.books.spring.DTOs.GenreDTO;
import com.books.spring.Models.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GenreMapper {
    GenreDTO toDto(Genre genre);

    @Mapping(target = "genreID", ignore = true)
    @Mapping(target = "books", ignore = true)
    Genre toEntity(GenreDTO dto);
}