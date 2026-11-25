package com.books.spring.DTOs;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

import com.books.spring.Misc.BooksJavaConstants.ValidationMessages;

public record FreePeriodDTO(
 
    @NotNull(message = ValidationMessages.FREE_FROM_REQUIRED)
    LocalDateTime freeFrom,

    @NotNull(message = ValidationMessages.FREE_UNTIL_REQUIRED)
    LocalDateTime freeUntil
)
{ }