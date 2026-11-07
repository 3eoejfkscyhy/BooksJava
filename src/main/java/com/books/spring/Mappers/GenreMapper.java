package com.books.spring.Mappers;

import com.books.spring.DTOs.GenreDTO;
import com.books.spring.Models.Book;
import com.books.spring.Models.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    @Mapping(target = "isbns", source = "books")
    GenreDTO toDto(Genre genre);

    @Mapping(target = "genreID", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    Genre toEntity(GenreDTO dto);

    default String bookToIsbn(Book book) { return book.getIsbn(); }

    @Named("activeGenres")
    default Set<GenreDTO> toActiveDtos(Set<Genre> genres) {
        return genres.stream()
                .filter(genre -> !genre.isDeleted())
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}