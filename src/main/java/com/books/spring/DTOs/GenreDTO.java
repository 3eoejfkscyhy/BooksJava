package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import java.util.Set;

import com.books.spring.Misc.BooksJavaConstants.*;

public record GenreDTO (
     Long genreID,

    @NotBlank(message = ValidationMessages.GENRE_NAME_REQUIRED)
    @Size(min = ValidationRules.GENRE_NAME_MIN_LENGTH, max = ValidationRules.GENRE_NAME_MAX_LENGTH, message = ValidationMessages.GENRE_NAME_SIZE_VALID)
     String genreName,

     Set<String> isbns
) {}