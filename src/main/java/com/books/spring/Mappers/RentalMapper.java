package com.books.spring.Mappers;

import com.books.spring.DTOs.RentalDTO;
import com.books.spring.Models.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RentalMapper {
    @Mapping(target = "userID", source = "user.userID")
    @Mapping(target = "isbn", source = "book.isbn")
    RentalDTO toDto(Rental rental);
}