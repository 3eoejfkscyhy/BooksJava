package com.books.spring.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FreePeriodDTO {
    @NotNull(message = "Free period has to have a start date and time")
    private LocalDateTime freeFrom;

    @NotNull(message = "Free period should have an end date and time")
    private LocalDateTime freeUntil;
}