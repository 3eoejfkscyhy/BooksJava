package com.books.spring.DTOs;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record FreePeriodDTO(
 
    @NotNull(message = "Free period has to have a start date and time")
    LocalDateTime freeFrom,

    @NotNull(message = "Free period should have an end date and time")
    LocalDateTime freeUntil
)
{ }