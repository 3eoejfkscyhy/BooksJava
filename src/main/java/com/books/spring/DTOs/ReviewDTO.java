package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ReviewDTO (
     Long reviewID,

    @NotBlank(message = "Headline is mandatory")
    @Size(min = 2, max = 40, message = "Headline must be between 2 and 40 characters")
     String headline,

    @Size(max = 500, message = "Review must be under 500 characters")
     String body,

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @NotNull
     BigDecimal stars,

    @NotBlank(message = "Book ISBN is required")
     String isbn,

    @NotNull(message = "User is required")
     Long userID,

     Boolean isDeleted
){}