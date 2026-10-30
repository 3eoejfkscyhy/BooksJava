package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

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

    //BigDecimal stars
    @Pattern(regexp = "[0-5]\\.[0,5]", message = "The given rating must be between 0 and 5 stars")
    @NotNull(message = "Rating is mandatory to post a review")
    private BigDecimal stars;

    // @NotNull(message = "Book ISBN is required")
    // private Long isbn;

}