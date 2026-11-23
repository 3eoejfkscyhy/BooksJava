package com.books.spring.DTOs;

import com.books.spring.Misc.BooksJavaConstants.ValidationMessages;
import com.books.spring.Misc.BooksJavaConstants.ValidationRules;

import jakarta.validation.constraints.*;

public record RentalRequestDTO(
    @NotNull(message = ValidationMessages.USER_ID_REQUIRED)
    @Positive(message = ValidationMessages.USER_ID_VALID)
     Long userID,

       @Pattern(
            regexp = ValidationRules.ISBN_PATTERN,
            message = ValidationMessages.ISBN_VALID_FORMAT
    )
    @NotBlank(message = ValidationMessages.ISBN_REQUIRED)
    String isbn
){}