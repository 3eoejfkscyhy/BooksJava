package com.books.spring.DTOs;

import com.books.spring.Models.Rental;


import java.time.LocalDateTime;

public record RentalDTO (
     Long rentalID,
     Long userID,
     String isbn,
     LocalDateTime rentedAt,
     LocalDateTime dueAt,
     LocalDateTime returnedAt,
     Rental.RentalStatus status
){}