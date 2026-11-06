package com.books.spring.Mappers;

import com.books.spring.DTOs.CollectionDTO;
import com.books.spring.Models.Book;
import com.books.spring.Models.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CollectionMapper {
    @Mapping(target = "userID", source = "user.userID")
    @Mapping(target = "isbns", source = "books")
    CollectionDTO toDto(Collection collection);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "collectionID", ignore = true)
    @Mapping(target = "books", ignore = true)
    Collection toEntity(CollectionDTO dto);

    default Long bookToIsbn(Book book) { return book.getIsbn(); }
}