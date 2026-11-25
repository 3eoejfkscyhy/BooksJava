package com.books.spring.Controllers;

import com.books.spring.DTOs.AuthorDTO;
import com.books.spring.Services.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.books.spring.Misc.BooksJavaConstants.Paths;

import java.util.List;

@RestController
@RequestMapping(Paths.AUTHOR_PATH)
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public List<AuthorDTO> getAll() {
        return authorService.getAll();
    } 

    @GetMapping(Paths.AUTHOR_ID)
    public AuthorDTO getByAuthorID(@PathVariable Long authorID) {
        return authorService.getByID(authorID);
    } 

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@Valid @RequestBody AuthorDTO dto) {
        return authorService.create(dto);
    }

    @PutMapping(Paths.AUTHOR_ID)
    public AuthorDTO update(@PathVariable Long authorID, @Valid @RequestBody AuthorDTO dto) {
        return authorService.update(authorID, dto);
    }

    @DeleteMapping(Paths.AUTHOR_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long authorID) {
        authorService.delete(authorID);
    }
}