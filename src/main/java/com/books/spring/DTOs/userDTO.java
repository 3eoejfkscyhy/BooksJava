package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
public class UserDTO {
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email has to be valid")
    private String email;

    private String status;

}