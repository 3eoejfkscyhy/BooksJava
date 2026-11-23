package com.books.spring.DTOs;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

import com.books.spring.Misc.BooksJavaConstants.ValidationMessages;
import com.books.spring.Misc.BooksJavaConstants.ValidationRules;

public record UserDTO (
     Long userID,

    @NotBlank(message = ValidationMessages.USERNAME_REQUIRED)
    @Size(min = ValidationRules.USERNAME_MIN_LENGTH, max = ValidationRules.USERNAME_MAX_LENGTH, message = ValidationMessages.USERNAME_LENGHT_SIZE)
     String username,

    @NotBlank(message =ValidationMessages.EMAIL_REQUIRED)
    @Email(message = ValidationMessages.EMAIL_VALID)
     String email,

    @NotBlank(message = ValidationMessages.PASSWORD_REQUIRED)
    @Size(min = ValidationRules.PASSWORD_MIN_LENGTH, message = ValidationMessages.PASSWORD_SIZE_VALID)
    @Pattern(regexp = ValidationRules.PASSWORD_PATTERN, message = ValidationMessages.PASSWORD_VALID_FORMAT)
     String password,

    @Size(max = ValidationRules.STATUS_MAX_LENGTH, message = ValidationMessages.STATUS_SIZE_VALID)
     String status,

     @Digits(integer = ValidationRules.BALANCE_WHOLE, fraction = ValidationRules.FRACTION)
     BigDecimal balance,

     Set<Long> reviewIDs,
     Set<Long> collectionIDs
){}