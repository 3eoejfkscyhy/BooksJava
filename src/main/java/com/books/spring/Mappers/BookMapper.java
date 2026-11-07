package com.books.spring.Mappers;

import com.books.spring.DTOs.BookDTO;
import com.books.spring.Models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "authorIDs", source = "bookAuthors")
    @Mapping(target = "genreIDs", source = "bookGenres")
    @Mapping(target = "reviewIDs", source = "reviews")
    @Mapping(target = "collectionIDs", source = "collections")
    BookDTO toDto(Book book);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "bookAuthors", ignore = true)
    @Mapping(target = "bookGenres", ignore = true)
    @Mapping(target = "collections", ignore = true)
    @Mapping(target = "BookSide", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    Book toEntity(BookDTO dto);

    default Long reviewToID(Review review) { return review.getReviewID(); }
    default Long collectionToID(Collection collection) { return collection.getCollectionID(); }
    default Long authorToID(Author author) { return author.getAuthorID(); }
    default Long genreToID(Genre genre) { return genre.getGenreID(); }

    @Named("activeBooks")
    default Set<BookDTO> toActiveDtos(Set<Book> books) {
        return books.stream()
                .filter(book -> !book.isDeleted())
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}