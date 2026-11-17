package com.books.spring.DTOs;

import jakarta.validation.constraints.*;

public record RentalRequestDTO(
    @NotNull(message = "Associated user is required")
    @Positive(message = "Associated user ID must be valid")
     Long userID,

    @NotBlank(message = "Associated isbn is required")
     String isbn
){}