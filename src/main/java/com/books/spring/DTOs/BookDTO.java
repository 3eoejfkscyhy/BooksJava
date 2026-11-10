package com.books.spring.DTOs;

import com.books.spring.Models.Book;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDTO {
    @NotBlank
    @Pattern(
            regexp = "97[89]\\d{10}",
            message = "ISBN must contain 13 digits and start with 978 or 979"
    )
    private String isbn;

    @NotBlank(message = "Book title is required")
    private String title;

    @NotNull
    @Positive(message = "Book length has to be valid")
    private Long bookLength;

    @Pattern(regexp = "[A-Za-z]+",message = "The language must be written with latin letters")
    private String language;

    @NotNull
    private Book.BookType bookType;

    @NotNull
    @PositiveOrZero(message = "Price is mandatory")
    private BigDecimal price;

    @PositiveOrZero(message = "Rental price cannot be negative")
    private BigDecimal rentalPrice;

    private Set<Long> authorIDs;
    private Set<Long> reviewIDs;
    private Set<Long> genreIDs;
    private Set<Long> collectionIDs;

    private boolean isDeleted;
}