package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class bookDTO {
    @Size(min=13, max=13, message = "The ISBN must be the ISBN-13 format")
    @Pattern(regexp = "97[8-9]", message = "The Begining must eighter start with 978 or 979")
    private long isbn;

    @NotBlank(message = "Book title is required")
    private String bookName;

    @Positive(message = "Book length has to be valid")
    private Long bookLength;

    @Pattern(regexp = "[A-Za-z]",message = "The language must be written with latin letters")
    private String language;

    @PositiveOrZero(message = "Price is mandatory")
    private BigDecimal price;


}