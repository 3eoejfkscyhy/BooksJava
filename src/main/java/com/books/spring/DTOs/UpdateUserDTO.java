package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record UpdateUserDTO( 

    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
     String username,

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
     String email,

    @Size(max = 100, message = "Status must not exceed 100 characters")
     String status,

    @PositiveOrZero
     BigDecimal balance
)
{}