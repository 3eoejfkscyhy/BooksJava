package com.books.spring.Controllers;

import com.books.spring.DTOs.UserBookDTO;
import com.books.spring.Models.UserBooks;
import com.books.spring.Services.UserBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-books")
@RequiredArgsConstructor
public class UserBookController {
    private final UserBookService userBookService;

    @GetMapping("/user/{userID}")
    public List<UserBookDTO> getByUserID(@PathVariable Long userID) {
        return userBookService.getByUserID(userID);
    }

    @PostMapping("/user/{userID}/book/{isbn}/purchase")
    public UserBookDTO purchase(@PathVariable Long userID, @PathVariable String isbn) {
        return userBookService.purchase(userID, isbn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserBookDTO create(@Valid @RequestBody UserBookDTO dto) {
        return userBookService.create(dto);
    }

    @PutMapping("/user/{userID}/book/{isbn}/status")
    public UserBookDTO update(@PathVariable Long userID, @PathVariable String isbn, @RequestParam UserBooks.Status status) {
        return userBookService.update(userID, isbn, status);
    }

    @DeleteMapping("/user/{userID}/book/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userID, @PathVariable String isbn) {
        userBookService.delete(userID, isbn);
    }
}