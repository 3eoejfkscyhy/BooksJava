package com.books.spring.DTOs;

import com.books.spring.Models.Book;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class bookDTO {
    private long isbn;

    @NotBlank(message = "Book title is required")
    private String bookName;

    @Positive(message = "Book length has to be valid")
    private Long bookLength;

    @NotNull(message = "Number of chapters is mandatory")
    private int chapters;

    @PositiveOrZero(message = "Price is mandatory")
    private BigDecimal price;

    public Book.Status bookStatus;

}