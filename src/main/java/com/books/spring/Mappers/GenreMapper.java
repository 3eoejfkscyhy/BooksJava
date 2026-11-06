package com.books.spring.Mappers;

import com.books.spring.DTOs.GenreDTO;
import com.books.spring.Models.Book;
import com.books.spring.Models.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    @Mapping(target = "isbns", source = "books")
    GenreDTO toDto(Genre genre);

    @Mapping(target = "genreID", ignore = true)
    @Mapping(target = "books", ignore = true)
    Genre toEntity(GenreDTO dto);

    default Long bookToIsbn(Book book) { return book.getIsbn(); }
}