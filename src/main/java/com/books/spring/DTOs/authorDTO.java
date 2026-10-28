package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
public class authorDTO {
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;

    // Last name is nullable so the author can input a pen name rather than their
    // legal name if they prefer
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

    @Positive(message = "Age is required")
    private int age;

    @PositiveOrZero(message = "Number of written books is required")
    private int booksWritten;

    private Set<Long> isbns;

}