package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

import com.books.spring.Misc.BooksJavaConstants.ValidationMessages;
import com.books.spring.Misc.BooksJavaConstants.ValidationRules;

public record ReviewDTO (
     Long reviewID,

    @NotBlank(message = ValidationMessages.HEADLINE_REQUIRED)
    @Size(min = ValidationRules.HEADLINE_MIN_LENGTH, max = ValidationRules.HEADLINE_MAX_LENGTH, message = ValidationMessages.HEADLINE_LENGHT_SIZE)
     String headline,

    @Size(max = ValidationRules.BODY_MAX_LENGHT, message = ValidationMessages.BODY_SIZE_VALID)
     String body,

    @DecimalMin(ValidationRules.STARS_MIN)
    @DecimalMax(ValidationRules.STARS_MIN)
    @NotNull
     BigDecimal stars,

    @NotBlank(message = ValidationMessages.ISBN_REQUIRED)
     String isbn,

    @NotNull(message = ValidationMessages.USER_ID_REQUIRED)
     Long userID
){}