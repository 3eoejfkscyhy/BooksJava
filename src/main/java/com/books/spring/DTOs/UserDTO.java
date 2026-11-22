package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

import com.books.spring.Misc.BooksJavaConstants.ValidationMessages;
import com.books.spring.Misc.BooksJavaConstants.ValidationRules;

public record UserDTO (
     Long userID,

     String username,

     String email,

     String password,

    @Size(max = ValidationRules.STATUS_MAX_LENGTH, message = ValidationMessages.STATUS_SIZE_VALID)
     String status,

    @PositiveOrZero(message = ValidationMessages.BALANCE_POSITIVE)
     BigDecimal balance,

     Set<Long> reviewIDs,
     Set<Long> collectionIDs
){}