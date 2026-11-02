package com.books.spring.Mappers;

import com.books.spring.DTOs.collectionDTO;
import com.books.spring.Models.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface collectionMapper {
    collectionDTO toDto(Collection collection);

    @Mapping(target = "user", ignore = true)
    Collection toEntity(collectionDTO dto);

}