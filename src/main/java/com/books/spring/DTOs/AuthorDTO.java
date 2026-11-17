package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

public record AuthorDTO(
  Long authorID,
  @NotBlank(message = "First name is required")
  @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
  String firstName,
  @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
  String lastName,
  @NotNull(message = "Author DOB is required")
  @Past(message = "The date of birth must be in the past")
  LocalDate DOB,
  @PositiveOrZero(message = "Number of written books is required")
   int booksWritten,
   Set<String> isbns,
   Boolean isDeleted
){}
   




