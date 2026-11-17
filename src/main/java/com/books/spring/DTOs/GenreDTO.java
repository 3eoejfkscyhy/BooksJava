package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import java.util.Set;

public record GenreDTO (
     Long genreID,

    @NotBlank(message = "Genre name is mandatory")
    @Size(min = 2, max = 50, message = "Genre name must be between 2 and 50 characters")
     String genreName,

     Set<String> isbns,

     Boolean isDeleted
) {}