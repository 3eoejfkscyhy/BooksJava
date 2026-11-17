package com.books.spring.DTOs;

import com.books.spring.Models.UserBooks;
import jakarta.validation.constraints.*;

public record UserBookDTO (
    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be valid")
     Long userID,

    @NotBlank
    @Pattern(regexp = "97[89]\\d{10}")
     String isbn,

     UserBooks.Status readingStatus
){}