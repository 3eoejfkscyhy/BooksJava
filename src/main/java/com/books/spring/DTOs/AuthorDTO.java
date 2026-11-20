package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

import com.books.spring.Misc.BooksJavaConstants.ValidationMessages;
import com.books.spring.Misc.BooksJavaConstants.ValidationRules;

public record AuthorDTO(
  Long authorID,
  @NotBlank(message = ValidationMessages.FIRST_NAME_REQUIRED)
  @Size(min = ValidationRules.AUTHOR_NAME_MIN_LENGTH, max = ValidationRules.AUTHOR_NAME_MAX_LENGTH, message = ValidationMessages.FIRST_NAME_LENGHT_SIZE)
  String firstName,
  @Size(min = ValidationRules.AUTHOR_NAME_MIN_LENGTH, max = ValidationRules.AUTHOR_NAME_MAX_LENGTH, message = ValidationMessages.LAST_NAME_LENGHT_SIZE)
  String lastName,
  @NotNull(message = ValidationMessages.AUTHOR_DOB_REQUIRED)
  @Past(message = ValidationMessages.AUTHOR_DOB_IN_PAST)
  LocalDate DOB,
  @PositiveOrZero(message =  ValidationMessages.BOOKS_WRITTEN_AMOUNT)
   int booksWritten,
   Set<String> isbns
){}
   




