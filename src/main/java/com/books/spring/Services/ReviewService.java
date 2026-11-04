package com.books.spring.Services;

import com.books.spring.DTOs.ReviewDTO;
import com.books.spring.Mappers.ReviewMapper;
import com.books.spring.Models.Author;
import com.books.spring.Models.Review;
import com.books.spring.Repositories.BookRepository;
import com.books.spring.Repositories.ReviewRepository;
import com.books.spring.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public List<ReviewDTO> getAll() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    public ReviewDTO getById(Long reviewID) { return reviewMapper.toDto(findReview(reviewID)); }

    public ReviewDTO create(ReviewDTO dto) {
        Review review = reviewMapper.toEntity(dto);
        review.setReviewID(null);
        review.setWrittenBy(
                userRepository.findById(dto.getUserID())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User " + dto.getUserID() + " could not be found"
                        ))
        );
        review.setBook(
                bookRepository.findByisbn(dto.getIsbn())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User " + dto.getIsbn() + " could not be found"
                        ))
        );
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    public ReviewDTO update(Long reviewID, ReviewDTO dto) {
        Review review = findReview(reviewID);
        review.setWrittenBy(
                userRepository.findById(dto.getUserID())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User " + dto.getUserID() + " could not be found"
                        ))
        );
        review.setBook(
                bookRepository.findByisbn(dto.getIsbn())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User " + dto.getIsbn() + " could not be found"
                        ))
        );
        review.setHeadline(dto.getHeadline());
        review.setBody(dto.getBody());
        review.setStars(dto.getStars());

        return reviewMapper.toDto(reviewRepository.save(review));
    }

    public Set<Review> findReviews(Set<Long> reviewIDs) {
        return reviewIDs.stream()
                .map(this::findReview)
                .collect(Collectors.toSet());
    }

    private Review findReview(Long reviewID){
        return reviewRepository.findById(reviewID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review " + reviewID + " was not found"));
    }
}