package com.books.spring.Controllers;

import com.books.spring.DTOs.GenreDTO;
import com.books.spring.Services.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public List<GenreDTO> getAll() {
        return genreService.getAll();
    }

    @GetMapping("/{genreID}")
    public GenreDTO getByIsbn(@PathVariable Long genreID) {
        return genreService.getById(genreID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDTO create(@Valid @RequestBody GenreDTO dto) {
        return genreService.create(dto);
    }

    @PutMapping("/{genreID}")
    public GenreDTO update(@PathVariable Long genreID, @Valid @RequestBody GenreDTO dto) {
        return genreService.update(genreID, dto);
    }

    @DeleteMapping("/{genreID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long genreID) {
        genreService.delete(genreID);
    }

    @Deprecated
    @DeleteMapping("/hard-delete/{genreID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hardDelete(@PathVariable Long genreID) {
        genreService.hardDelete(genreID);
    }
}