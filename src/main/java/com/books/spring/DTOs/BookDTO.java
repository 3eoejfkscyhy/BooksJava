package com.books.spring.DTOs;

import com.books.spring.Models.Author;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDTO {
    @Pattern(
            regexp = "97[8-9]\\d{10}",
            message = "ISBN must be a valid 13-digit ISBN starting with 978 or 979"
    )
    private String isbn;

    @NotBlank(message = "Book title is required")
    private String title;

    @Positive(message = "Book length has to be valid")
    private Long bookLength;

    @Pattern(regexp = "[A-Za-z]+",message = "The language must be written with latin letters")
    private String language;

    @NotNull
    @PositiveOrZero(message = "Price is mandatory")
    private BigDecimal price;

    private Set<Long> authorIDs;
    private Set<Long> reviewIDs;
    private Set<Long> genreIDs;
    private Set<Long> collectionIDs;

    private boolean isDeleted;
}