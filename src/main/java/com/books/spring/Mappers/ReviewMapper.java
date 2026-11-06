package com.books.spring.Mappers;

import com.books.spring.DTOs.ReviewDTO;
import com.books.spring.Models.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "userID", source = "writtenBy.userID")
    @Mapping(target = "isbn", source = "book.isbn")
    ReviewDTO toDto(Review review);

    @Mapping(target = "reviewID", ignore = true)
    @Mapping(target = "writtenBy", ignore = true)
    @Mapping(target = "book", ignore = true)
    Review toEntity(ReviewDTO dto);

}