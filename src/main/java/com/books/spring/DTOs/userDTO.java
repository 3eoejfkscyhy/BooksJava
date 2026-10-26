package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class userDTO {
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 20, message = "username must be between 2 and 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Size(min = 11, max = 30, message = "Email must be between 11 and 30 characters")
    @Email(message = "Email has to be valid")
    private String email;

}