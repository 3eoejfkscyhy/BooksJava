package com.books.spring.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentalRequestDTO {
    @NotNull(message = "Associated user is required")
    @Positive(message = "Associated user ID must be valid")
    private Long userID;

    @NotBlank(message = "Associated isbn is required")
    private String isbn;
}