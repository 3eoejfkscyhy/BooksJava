package com.books.spring.Mappers;

import com.books.spring.DTOs.BookDTO;
import com.books.spring.Models.Author;
import com.books.spring.Models.Book;
import com.books.spring.Models.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookMapper {

    @Mapping(target = "authorIDs", source = "bookAuthors")
    @Mapping(target = "genreIDs", source = "bookGenres")
    BookDTO toDto(Book book);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "bookAuthors", ignore = true)
    @Mapping(target = "bookGenres", ignore = true)
    @Mapping(target = "collections", ignore = true)
    @Mapping(target = "BookSide", ignore = true)
    Book toEntity(BookDTO dto);

    default Long authorToID(Author author) { return author.getAuthorID(); }
    default Long genreToID(Genre genre) { return genre.getGenreID(); }
}