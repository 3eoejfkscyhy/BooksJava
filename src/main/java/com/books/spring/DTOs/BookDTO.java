package com.books.spring.DTOs;

import com.books.spring.Misc.BooksJavaConstants.ValidationMessages;
import com.books.spring.Misc.BooksJavaConstants.ValidationRules;
import com.books.spring.Models.Book;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record BookDTO(
    @Pattern(
            regexp = ValidationRules.ISBN_PATTERN,
            message = ValidationMessages.ISBN_VALID_FORMAT
    )
    @NotBlank(message = ValidationMessages.ISBN_REQUIRED)
    String isbn,
    @NotBlank(message = ValidationMessages.BOOK_TITLE_REQUIRED)
     String title,
    @NotNull
    @Positive(message = ValidationMessages.BOOK_LENGHT_VALID)
     Long bookLength,
    @Pattern(regexp = ValidationRules.LANGUAGE_PATTERN, message = ValidationMessages.LANGUAGE_MUST_BE_LATIN_LETTERS)
     String language,
    @NotNull(message = ValidationMessages.BOOK_TYPE_REQUIRED)
     Book.BookType bookType,
    @NotNull(message = ValidationMessages.PRICE_REQUIRED)
    @PositiveOrZero(message = ValidationMessages.PRICE_POSITIVE)
   @Digits(integer = ValidationRules.PRICE_WHOLE, fraction = ValidationRules.FRACTION)
     BigDecimal price,

    @PositiveOrZero(message = ValidationMessages.RENTAL_PRICE_POSITIVE)
    @Digits(integer = ValidationRules.PRICE_WHOLE, fraction = ValidationRules.FRACTION)
     BigDecimal rentalPrice,

     Set<Long> authorIDs,
     Set<Long> reviewIDs,
     Set<Long> genreIDs,
     Set<Long> collectionIDs,
    LocalDateTime freeFrom,
    LocalDateTime freeUntil
){}