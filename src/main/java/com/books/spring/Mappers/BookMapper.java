package com.books.spring.Mappers;

import com.books.spring.DTOs.BookDTO;
import com.books.spring.Models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookMapper {
    BookDTO toDto(Book book);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "bookAuthors", ignore = true)
    @Mapping(target = "booksGenres", ignore = true)
    @Mapping(target = "collections", ignore = true)
    @Mapping(target = "BookSide", ignore = true)
    Book toEntity(BookDTO dto);
}