package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDTO {
    private Long reviewID;

    @NotBlank(message = "Headline is mandatory")
    @Size(min = 2, max = 40, message = "Headline must be between 2 and 40 characters")
    private String headline;

    @Size(max = 500, message = "Review must be under 500 characters")
    private String body;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @NotNull
    private BigDecimal stars;

    @NotNull(message = "Book ISBN is required")
    private String isbn;

    @NotNull(message = "User is required")
    private Long userID;

    private boolean isDeleted;
}