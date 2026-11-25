package com.books.spring.Services;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import com.books.spring.DTOs.ReviewDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Mappers.ReviewMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Misc.BooksJavaFunctions;
import com.books.spring.Models.Review;
import com.books.spring.Repositories.ReviewRepository;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;


import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {
      @Mock
    private ReviewRepository reviewRepository;
     @Mock
     private ReviewMapper reviewMapper;
     @InjectMocks 
     private ReviewService reviewService;


@Test
void getByHeadlineReturnsMappedReview()
{
    Review sucks = Review.builder().reviewID(1L).headline("It sucked").body("It just did").stars(new BigDecimal(0.50)).isEdited(false).build();
    ReviewDTO SucksDTO = new ReviewDTO(null, null, null, null, null, null, null, null);
    when(reviewRepository.findById(1L)).thenReturn(Optional.of(sucks));
    when(reviewMapper.toDto(sucks)).thenReturn(SucksDTO);

    ReviewDTO result = reviewService.getById(1L);
    assertThat(result).isSameAs(SucksDTO);
}


@Test
void getByIdThrowsNotFoundForAMissingReview()
{
    when(reviewRepository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(()->reviewService.getById(99L)).isInstanceOf((NotFoundException.class)).hasMessage(ExceptionMessages.GIVEN_REVIEW_NOT_FOUND.formatted(99L));
}

@Test
void createSavesTheReviewWithourTheIdSentByTheClient()
{
    ReviewDTO request = reviewDTO(7L,"It sucked","It just did",new BigDecimal(0.50),2L,LocalDate.now(),false);
    Review Sucks = Review.builder().reviewID(1L).headline("It sucked").body("It just did").stars(new BigDecimal(0.50)).isEdited(false).build();
     ReviewDTO response = new ReviewDTO(null, null, null, null, null, null, null, null);
     when(reviewMapper.toEntity(request)).thenReturn((Sucks));
     when(reviewRepository.save(Sucks)).thenReturn((Sucks));
     when(reviewMapper.toDto(Sucks)).thenReturn((response));

     ReviewDTO result = reviewService.create(request);
     assertThat(result).isSameAs(response); 
     assertThat(Sucks.getReviewID()).isNull(); 
}

@Test 
void updateAcceptsTheReviewsOwnID()
{
       Review Sucks = Review.builder().reviewID(1L).headline("It sucked").body("It just did").stars(new BigDecimal(0.50)).isEdited(false).build();
    when(reviewRepository.findById(1L)).thenReturn(Optional.of(Sucks));

    reviewService.update(1L, reviewDTO(1L,"It sucked","It just did",new BigDecimal(0.50),2L,LocalDate.now(),false));
    verify(reviewRepository,never()).existsById(any());
    verify(reviewRepository).save(Sucks);
}


           @Test
    void givenInvalidStarsShouldGiveConstraintViolation() throws Exception {
       Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        ReviewDTO review =  BooksJavaFunctions.review(1L, "This is the most funniest book i have read", "Like every great lasagna, Garfield was born in the kitchen of an Italian restaurant on a winter's night in 1978, while outside snow fell like grated Parmesan cheese. He weighed five pounds, six ounces at birth—that's big for a kitten!—and right from the start showed a passion for Italian food",
                                     new BigDecimal(48.502), "9780545437643",5L);
     Set<ConstraintViolation<ReviewDTO>> violations = validator.validate(review);
     assertThat(violations).hasSize(2);
     assertThat(violations).extracting("message").contains("numeric value out of bounds (<1 digits>.<2 digits> expected)");
    }

    private static ReviewDTO reviewDTO(Long reviewID,String headline,String body, BigDecimal stars,Long userID, LocalDate leftAt,Boolean isEdited)
    {
       return new ReviewDTO(reviewID, headline, body, stars,null ,userID, leftAt, isEdited);
    }
}