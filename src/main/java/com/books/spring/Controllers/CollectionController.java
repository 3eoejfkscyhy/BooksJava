package com.books.spring.Controllers;

import com.books.spring.DTOs.CollectionDTO;
import com.books.spring.Services.CollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.books.spring.Misc.BooksJavaConstants.Paths;

import java.util.List;

@RestController
@RequestMapping(Paths.COLLECTION_PATH)
@RequiredArgsConstructor
public class CollectionController {
    private final CollectionService collectionService;

    @GetMapping
    public List<CollectionDTO> getAll() {
        return collectionService.getAll();
    }

    @GetMapping(Paths.COLLECTION_ID)
    public CollectionDTO getById(@PathVariable Long collectionID) {
        return collectionService.getById(collectionID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CollectionDTO create(@Valid @RequestBody CollectionDTO dto) {
        return collectionService.create(dto);
    }

    @PutMapping(Paths.COLLECTION_ID)
    public CollectionDTO update(@PathVariable Long collectionID, @Valid @RequestBody CollectionDTO dto) {
        return collectionService.update(collectionID, dto);
    }

    @DeleteMapping(Paths.COLLECTION_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long collectionID) {
        collectionService.delete(collectionID);
    }
}