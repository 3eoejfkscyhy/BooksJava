package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

public record UserDTO (
     Long userID,

     String username,

     String email,

    @Size(max = 100, message = "Status must be under 100 characters long")
     String status,

    @PositiveOrZero(message = "The balance must be positive or empty")
     BigDecimal balance,

     Set<Long> reviewIDs,
     Set<Long> collectionIDs,

     Boolean isDeleted
){}