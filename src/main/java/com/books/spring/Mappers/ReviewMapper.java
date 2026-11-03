package com.books.spring.Mappers;

import com.books.spring.DTOs.ReviewDTO;
import com.books.spring.Models.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ReviewMapper {
    ReviewDTO toDto(Review review);

    @Mapping(target = "reviewID", ignore = true)
    @Mapping(target = "writtenBy", ignore = true)
    @Mapping(target = "book", ignore = true)
    Review toEntity(ReviewDTO dto);

}