package com.books.spring.Controllers;

import com.books.spring.DTOs.ReviewDTO;
import com.books.spring.Services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.books.spring.Misc.BooksJavaConstants.Paths;

import java.util.List;

@RestController
@RequestMapping(Paths.REVIEW_PATH)
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewDTO> getAll() {
        return reviewService.getAll();
    }

    @GetMapping(Paths.REVIEW_ID)
    public ReviewDTO getByIsbn(@PathVariable Long reviewID) {
        return reviewService.getById(reviewID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO create(@Valid @RequestBody ReviewDTO dto) {
        return reviewService.create(dto);
    }

    @PutMapping(Paths.REVIEW_ID)
    public ReviewDTO update(@PathVariable Long reviewID, @Valid @RequestBody ReviewDTO dto) {
        return reviewService.update(reviewID, dto);
    }

    @DeleteMapping(Paths.REVIEW_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long reviewID) {
        reviewService.delete(reviewID);
    }
}