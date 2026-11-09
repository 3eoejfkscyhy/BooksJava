package com.books.spring.DTOs;

import com.books.spring.Models.Rental;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentalDTO {
    private Long rentalID;
    private Long userID;
    private String isbn;
    private LocalDateTime rentedAt;
    private LocalDateTime dueAt;
    private LocalDateTime returnedAt;
    private Rental.RentalStatus status;
}