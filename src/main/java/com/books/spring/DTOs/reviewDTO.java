package com.books.spring.DTOs;

import com.books.spring.Models.Review;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class reviewDTO {
    private Long reviewID;

    //string headline
    @NotBlank(message = "Headline is mandatory")
    @Size(min = 2, max = 40, message = "Headline must be between 2 and 40 characters")
    private String headline;

    //string body
    @Size(max = 500, message = "Review must be under 500 characters")
    private String body;

    //Review.Stars stars
    @NotNull(message = "Rating is mandatory to post a review")
    private Review.Stars stars;

    @NotNull(message = "Book ISBN is required")
    private Long isbn;

}