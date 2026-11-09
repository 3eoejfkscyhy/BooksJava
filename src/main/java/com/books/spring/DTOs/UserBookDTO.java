package com.books.spring.DTOs;

import com.books.spring.Models.UserBooks;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBookDTO {
    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be valid")
    private Long userID;

    @NotBlank
    @Pattern(regexp = "97[89]\\d{10}")
    private String isbn;

    private UserBooks.Status readingStatus;
}