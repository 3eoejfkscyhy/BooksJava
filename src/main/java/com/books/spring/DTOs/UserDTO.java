package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
public class UserDTO {
    private Long userid;

    @Size(max = 100, message = "Status must be under 100 characters long")
    private String status;

    @PositiveOrZero(message = "The balance must be positive or empty")
    private BigDecimal balance;

}