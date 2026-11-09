package com.books.spring.Controllers;

import com.books.spring.DTOs.RentalDTO;
import com.books.spring.DTOs.RentalRequestDTO;
import com.books.spring.Services.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RentalDTO create(@Valid @RequestBody RentalRequestDTO dto) {
        return rentalService.create(dto);
    }

    @PutMapping("/{rentalID}/return")
    public RentalDTO returnBook(@PathVariable Long rentalID) {
        return rentalService.returnBook(rentalID);
    }
}