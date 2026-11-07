package com.books.spring.Controllers;

import com.books.spring.DTOs.CollectionDTO;
import com.books.spring.Services.CollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
public class CollectionController {
    private final CollectionService collectionService;

    @GetMapping
    public List<CollectionDTO> getAll() {
        return collectionService.getAll();
    }

    @GetMapping("/{collectionID}")
    public CollectionDTO getById(@PathVariable Long collectionID) {
        return collectionService.getById(collectionID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CollectionDTO create(@Valid @RequestBody CollectionDTO dto) {
        return collectionService.create(dto);
    }

    @PutMapping("/{collectionID}")
    public CollectionDTO update(@PathVariable Long collectionID, @Valid @RequestBody CollectionDTO dto) {
        return collectionService.update(collectionID, dto);
    }

    @DeleteMapping("/{collectionID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long collectionID) {
        collectionService.delete(collectionID);
    }

    @Deprecated
    @DeleteMapping("/hard-delete/{collectionID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hardDelete(@PathVariable Long collectionID) {
        collectionService.hardDelete(collectionID);
    }
}