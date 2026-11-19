package com.books.spring.DTOs;

import com.books.spring.Misc.BooksJavaConstants.ValidationMessages;

import jakarta.validation.constraints.*;

public record RentalRequestDTO(
    @NotNull(message = ValidationMessages.USER_ID_REQUIRED)
    @Positive(message = ValidationMessages.USER_ID_VALID)
     Long userID,

    @NotBlank(message = ValidationMessages.ISBN_REQUIRED)
     String isbn
){}