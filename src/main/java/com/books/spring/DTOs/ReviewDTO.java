package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.books.spring.Misc.BooksJavaConstants.*;

public record ReviewDTO (
     Long reviewID,

    @NotBlank(message = ValidationMessages.HEADLINE_REQUIRED)
    @Size(max = ValidationRules.HEADLINE_MAX_LENGTH, message = ValidationMessages.HEADLINE_LENGHT_SIZE)
     String headline,

    @Size(max = ValidationRules.BODY_MAX_LENGHT, message = ValidationMessages.BODY_SIZE_VALID)
     String body,

    @Digits(integer = ValidationRules.STARS_WHOLE, fraction = ValidationRules.FRACTION)
    @DecimalMin(ValidationRules.STARS_MIN)
    @DecimalMax(ValidationRules.STARS_MAX)
    @NotNull(message = ValidationMessages.STARS_REQUIRED)
     BigDecimal stars,

        @Pattern(
            regexp = ValidationRules.ISBN_PATTERN,
            message = ValidationMessages.ISBN_VALID_FORMAT
    )
    @NotBlank(message = ValidationMessages.ISBN_REQUIRED)
    String isbn,

    @NotNull(message = ValidationMessages.USER_ID_REQUIRED)
    @Positive(message = ValidationMessages.USER_ID_VALID)
     Long userID,
             
     LocalDate leftAt, 
     
     Boolean isEdited
){}