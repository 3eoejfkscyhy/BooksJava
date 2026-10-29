package com.books.spring.DTOs;

import java.util.Set;

import com.books.spring.Models.Book;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class collectionDTO {
    private Long id;

    @NotBlank(message = "Collection name is required")
    @Size(max = 200, message = "Collection name must not exceed 200 characters")
    private String collectionName;

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be valid")
    private Long userID;

    private Set<Book> isbn;

}