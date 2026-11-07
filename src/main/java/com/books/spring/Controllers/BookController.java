package com.books.spring.Controllers;

import com.books.spring.DTOs.BookDTO;
import com.books.spring.Services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDTO> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{isbn}")
    public BookDTO getByIsbn(@PathVariable String isbn) {
        return bookService.getByIsbn(isbn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@Valid @RequestBody BookDTO dto) {
        return bookService.create(dto);
    }

    @PutMapping("/{isbn}")
    public BookDTO update(@PathVariable String isbn, @Valid @RequestBody BookDTO dto) {
        return bookService.update(isbn, dto);
    }

    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String isbn) {
        bookService.delete(isbn);
    }

    @Deprecated
    @DeleteMapping("/hard-delete/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hardDelete(@PathVariable String isbn) {
        bookService.hardDelete(isbn);
    }
}