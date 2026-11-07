package com.books.spring.Controllers;

import com.books.spring.DTOs.AuthorDTO;
import com.books.spring.Services.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public List<AuthorDTO> getAll() {
        return authorService.getAll();
    }

    @GetMapping("/{authorID}")
    public AuthorDTO getByAuthorID(@PathVariable Long authorID) {
        return authorService.getByID(authorID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@Valid @RequestBody AuthorDTO dto) {
        return authorService.create(dto);
    }

    @PutMapping("/{authorID}")
    public AuthorDTO update(@PathVariable Long authorID, @Valid @RequestBody AuthorDTO dto) {
        return authorService.update(authorID, dto);
    }

    @DeleteMapping("/{authorID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long authorID) {
        authorService.delete(authorID);
    }

    @Deprecated
    @DeleteMapping("/hard-delete/{authorID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hardDelete(@PathVariable Long authorID) {
        authorService.hardDelete(authorID);
    }
}