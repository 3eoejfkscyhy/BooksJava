package com.books.spring.Mappers;

import com.books.spring.DTOs.CollectionDTO;
import com.books.spring.Models.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CollectionMapper {
    CollectionDTO toDto(Collection collection);

    @Mapping(target = "user", ignore = true)
    Collection toEntity(CollectionDTO dto);

}