package com.books.spring.DTOs;

import com.books.spring.Misc.BooksJavaConstants.ValidationMessages;
import com.books.spring.Misc.BooksJavaConstants.ValidationRules;
import com.books.spring.Models.Rental;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record RentalDTO (
     Long rentalID,
     @NotNull(message = ValidationMessages.USER_ID_REQUIRED)
      @Positive(message =  ValidationMessages.USER_ID_VALID)
     Long userID,
     @Pattern(
            regexp = ValidationRules.ISBN_PATTERN,
            message = ValidationMessages.ISBN_VALID_FORMAT
     )
     @NotBlank(message = ValidationMessages.ISBN_REQUIRED)
     String isbn,
     LocalDateTime rentedAt,
     LocalDateTime dueAt,
     LocalDateTime returnedAt,
     Rental.RentalStatus status,
     Boolean isDeleted
){}