package com.books.spring.DTOs;

import com.books.spring.Models.Book;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

public record BookDTO(
    @Pattern(
            regexp = "97[89]\\d{10}",
            message = "ISBN must contain 13 digits and start with 978 or 979"
    )
    String isbn,
    @NotBlank(message = "Book title is required")
     String title,
    @NotNull
    @Positive(message = "Book length has to be valid")
     Long bookLength,
    @Pattern(regexp = "[A-Za-z]+",message = "The language must be written with latin letters")
     String language,
    @NotNull
     Book.BookType bookType,
    @NotNull
    @PositiveOrZero(message = "Price is mandatory")
     BigDecimal price,

    @PositiveOrZero(message = "Rental price cannot be negative")
     BigDecimal rentalPrice,

     Set<Long> authorIDs,
     Set<Long> reviewIDs,
     Set<Long> genreIDs,
     Set<Long> collectionIDs,

     Boolean isDeleted
){}