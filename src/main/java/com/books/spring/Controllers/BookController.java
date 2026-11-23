package com.books.spring.Controllers;

import com.books.spring.DTOs.BookDTO;
import com.books.spring.DTOs.FreePeriodDTO;
import com.books.spring.Exception.ConflictException;
import com.books.spring.Misc.BooksJavaConstants.*;
import com.books.spring.Services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Paths.BOOK_PATH)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDTO> getAll() {
        return bookService.getAll();
    }

    @GetMapping(Paths.BOOK_ISBN)
    public BookDTO getByIsbn(@PathVariable String isbn) {
        if(isNumeric(isbn) && isbn.length()==ValidationRules.ISBN_LENGTH)
         {
        return bookService.getByIsbn(isbn);
         }
          else
            throw new ConflictException(ValidationMessages.ISBN_VALID_FORMAT);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@Valid @RequestBody BookDTO dto) {
        return bookService.create(dto);
    }

    @PutMapping(Paths.BOOK_ISBN)
    public BookDTO update(@PathVariable String isbn, @Valid @RequestBody BookDTO dto) {
  if(isNumeric(isbn) && isbn.length()==ValidationRules.ISBN_LENGTH)
         {
        return bookService.update(isbn, dto);
         }
        else
            throw new ConflictException(ValidationMessages.ISBN_VALID_FORMAT);
    }

    @PutMapping(Paths.BOOK_ISBN_FREE_PERIOD)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BookDTO setFreePeriod(@PathVariable String isbn, @Valid @RequestBody FreePeriodDTO dto) {
          if(isNumeric(isbn) && isbn.length()==ValidationRules.ISBN_LENGTH)
         {
        return bookService.setFreePeriod(isbn, dto);
         }
        else
            throw new ConflictException(ValidationMessages.ISBN_VALID_FORMAT);
    }

    @DeleteMapping(Paths.BOOK_ISBN_FREE_PERIOD)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BookDTO removeFreePeriod(@PathVariable String isbn) {
          if(isNumeric(isbn) && isbn.length()==ValidationRules.ISBN_LENGTH)
         {
        return bookService.removeFreePeriod(isbn);
         }
        else
            throw new ConflictException(ValidationMessages.ISBN_VALID_FORMAT);
    }

    @DeleteMapping(Paths.BOOK_ISBN)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String isbn) {
        if(isNumeric(isbn) && isbn.length()==ValidationRules.ISBN_LENGTH)
         {
        bookService.delete(isbn);
        }
          else
       throw new ConflictException(ValidationMessages.ISBN_VALID_FORMAT);
    }

     public static boolean isNumeric(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        Long.parseLong(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
}
}