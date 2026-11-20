package com.books.spring.DTOs;

import com.books.spring.Misc.BooksJavaConstants.ValidationMessages;
import com.books.spring.Misc.BooksJavaConstants.ValidationRules;
import com.books.spring.Models.UserBooks;
import jakarta.validation.constraints.*;

public record UserBookDTO (
    @NotNull(message = ValidationMessages.USER_ID_REQUIRED)
    @Positive(message =  ValidationMessages.USER_ID_VALID)
     Long userID,

    @NotBlank
    @Pattern(regexp = ValidationRules.ISBN_PATTERN)
     String isbn,

     UserBooks.Status readingStatus,

    @NotNull
    @Positive(message =  ValidationMessages.BOOK_LENGHT_VALID)
     Long lastReadTo,

     String readingPercentage
){ }