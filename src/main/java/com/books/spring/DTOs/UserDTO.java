package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
public class UserDTO {
    private Long userID;

    private String username;

    private String email;

    @Size(max = 100, message = "Status must be under 100 characters long")
    private String status;

    @PositiveOrZero(message = "The balance must be positive or empty")
    private BigDecimal balance;

    private Set<Long> reviewIDs;
    private Set<Long> collectionIDs;

    private Boolean isDeleted;
}