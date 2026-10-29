package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
public class userDTO {
    private Long userid;

    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email has to be valid")
    @Size(max = 200, message = "Email length has to be valid")
    private String email;

    private String status;

    @PositiveOrZero(message = "The balance must be positive or empty")
    private BigDecimal balance;

}