package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenreDTO {
    private Long genreID;

    @NotBlank(message = "Genre name is mandatory")
    @Size(min = 2, max = 50, message = "Genre name must be between 2 and 50 characters")
    private String genreName;

    private Set<Long> isbns;

}