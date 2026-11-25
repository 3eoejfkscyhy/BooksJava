package com.books.spring.Mappers;

import com.books.spring.DTOs.AuthorDTO;
import com.books.spring.Models.Author;
import com.books.spring.Models.Book;

import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(target = "isbns", source = "books")
    AuthorDTO toDto(Author author);

    @Mapping(target = "authorID", ignore = true)
    @Mapping(target = "books", ignore = true)
    Author toEntity(AuthorDTO dto);

     default String bookToIsbn(Book book) { return book.getIsbn(); }

    @Named("activeAuthors")
    default Set<AuthorDTO> toActiveDtos(Set<Author> authors) {
        return authors.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}