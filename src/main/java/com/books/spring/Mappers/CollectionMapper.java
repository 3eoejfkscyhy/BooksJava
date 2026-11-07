package com.books.spring.Mappers;

import com.books.spring.DTOs.AuthorDTO;
import com.books.spring.DTOs.CollectionDTO;
import com.books.spring.Models.Author;
import com.books.spring.Models.Book;
import com.books.spring.Models.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CollectionMapper {
    @Mapping(target = "userID", source = "user.userID")
    @Mapping(target = "isbns", source = "books")
    CollectionDTO toDto(Collection collection);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "collectionID", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    Collection toEntity(CollectionDTO dto);

    default String bookToIsbn(Book book) { return book.getIsbn(); }

    @Named("activeCollections")
    default Set<CollectionDTO> toActiveDtos(Set<Collection> collections) {
        return collections.stream()
                .filter(collection -> !collection.isDeleted())
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}