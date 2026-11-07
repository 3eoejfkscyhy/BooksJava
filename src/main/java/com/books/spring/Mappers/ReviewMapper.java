package com.books.spring.Mappers;

import com.books.spring.DTOs.AuthorDTO;
import com.books.spring.DTOs.ReviewDTO;
import com.books.spring.Models.Author;
import com.books.spring.Models.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "userID", source = "writtenBy.userID")
    @Mapping(target = "isbn", source = "book.isbn")
    ReviewDTO toDto(Review review);

    @Mapping(target = "reviewID", ignore = true)
    @Mapping(target = "writtenBy", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    Review toEntity(ReviewDTO dto);

    @Named("activeReviews")
    default Set<ReviewDTO> toActiveDtos(Set<Review> reviews) {
        return reviews.stream()
                .filter(review -> !review.isDeleted())
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}