package com.books.spring.Mappers;

import com.books.spring.DTOs.AuthorDTO;
import com.books.spring.Models.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO toDto(Author author);

    @Mapping(target = "authorID", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    Author toEntity(AuthorDTO dto);

    @Named("activeAuthors")
    default Set<AuthorDTO> toActiveDtos(Set<Author> authors) {
        return authors.stream()
                .filter(author -> !author.isDeleted())
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}