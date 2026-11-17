package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record RegisterUserDTO( 
    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
    String username,

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be atleast 8 characters long")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "The password must only include letters and/or numbers")
    String password,

    @Size(max = 100, message = "Status must be under 100 characters long")
    String status,

     BigDecimal balance
)
{}