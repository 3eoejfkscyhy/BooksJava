package com.books.spring.Controllers;

import com.books.spring.DTOs.ReviewDTO;
import com.books.spring.Services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewDTO> getAll() {
        return reviewService.getAll();
    }

    @GetMapping("/{reviewID}")
    public ReviewDTO getByIsbn(@PathVariable Long reviewID) {
        return reviewService.getById(reviewID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO create(@Valid @RequestBody ReviewDTO dto) {
        return reviewService.create(dto);
    }

    @PutMapping("/{reviewID}")
    public ReviewDTO update(@PathVariable Long reviewID, @Valid @RequestBody ReviewDTO dto) {
        return reviewService.update(reviewID, dto);
    }

    @DeleteMapping("/{reviewID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long reviewID) {
        reviewService.delete(reviewID);
    }

    @Deprecated
    @DeleteMapping("/hard-delete/{reviewID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hardDelete(@PathVariable Long reviewID) {
        reviewService.hardDelete(reviewID);
    }
}