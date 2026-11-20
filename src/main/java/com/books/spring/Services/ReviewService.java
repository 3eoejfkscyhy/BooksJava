package com.books.spring.Services;

import com.books.spring.DTOs.ReviewDTO;
import com.books.spring.Mappers.ReviewMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Models.Review;
import com.books.spring.Repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.books.spring.Exception.*;

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
                userRepository.findById(dto.userID())
                        .orElseThrow(() -> new NotFoundException(
                                ExceptionMessages.GIVEN_USER_NOT_FOUND.formatted(dto.userID())
                        ))
        );
        review.setBook(
                bookRepository.findById(dto.isbn())
                        .orElseThrow(() -> new NotFoundException(
                                ExceptionMessages.GIVEN_BOOK_NOT_FOUND.formatted(dto.isbn())
                        ))
        );
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    public ReviewDTO update(Long reviewID, ReviewDTO dto) {
        Review review = findReview(reviewID);
        review.setWrittenBy(
                userRepository.findById(dto.userID())
                        .orElseThrow(() -> new NotFoundException(
                                 ExceptionMessages.GIVEN_USER_NOT_FOUND.formatted(dto.userID())
                        ))
        );
        review.setBook(
                bookRepository.findById(dto.isbn())
                        .orElseThrow(() -> new NotFoundException(
                                ExceptionMessages.GIVEN_REVIEW_NOT_FOUND.formatted(dto.isbn())
                        ))
        );
        review.setHeadline(dto.headline());
        review.setBody(dto.body());
        review.setStars(dto.stars());

        return reviewMapper.toDto(reviewRepository.save(review));
    }

    public void delete(Long reviewID) {
        reviewRepository.delete(findReview(reviewID));
    }

    public Set<Review> findReviews(Set<Long> reviewIDs) {
        return reviewIDs.stream()
                .map(this::findReview)
                .collect(Collectors.toSet());
    }

    private Review findReview(Long reviewID){
        return reviewRepository.findById(reviewID)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.GIVEN_REVIEW_NOT_FOUND.formatted(reviewID)));
    }
}