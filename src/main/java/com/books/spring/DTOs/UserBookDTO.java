package com.books.spring.DTOs;

import com.books.spring.Models.UserBooks;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record UserBookDTO (
    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be valid")
     Long userID,

    @NotBlank
    @Pattern(regexp = "97[89]\\d{10}")
     String isbn,

     UserBooks.Status readingStatus,

    @NotNull
    @Positive(message = "Book length has to be valid")
     Long lastReadTo,

     BigDecimal readingPercentage
){ }