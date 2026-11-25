package com.books.spring.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.math.BigDecimal;
import java.util.*;

import org.springframework.test.web.servlet.MockMvc;

import com.books.spring.DTOs.ReviewDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Misc.BooksJavaFunctions;
import com.books.spring.Misc.BooksJavaConstants.*;
import com.books.spring.Services.ReviewService;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {
     @Autowired 
     private MockMvc mockMvc;

     @MockitoBean 
     private ReviewService reviewService;


      @Test
    void getAllReturnsTheReviewsAsJson() throws Exception {
        when(reviewService.getAll()).thenReturn(List.of( BooksJavaFunctions.review(1L, "This is the most funniest book i have read", "Like every great lasagna, Garfield was born in the kitchen of an Italian restaurant on a winter's night in 1978, while outside snow fell like grated Parmesan cheese. He weighed five pounds, six ounces at birth—that's big for a kitten!—and right from the start showed a passion for Italian food",
                                     new BigDecimal(4.50), "9780545437643",5L),
                                                    BooksJavaFunctions.review(2L, "Do not recommend!", "",
                                     new BigDecimal(1.50), "9780545437643",9L)));

        mockMvc.perform(get(Paths.REVIEW_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].headline").value("This is the most funniest book i have read"))
                .andExpect(jsonPath("$[1].headline").value("Do not recommend!"));
    }



            @Test 
     void createReturns201AndSavedReview() throws Exception{
        ReviewDTO savedReview = BooksJavaFunctions.review(1L, "This is the most funniest book i have read", "Like every great lasagna, Garfield was born in the kitchen of an Italian restaurant on a winter's night in 1978, while outside snow fell like grated Parmesan cheese. He weighed five pounds, six ounces at birth—that's big for a kitten!—and right from the start showed a passion for Italian food",
                                     new BigDecimal(4.50), "9780545437643",5L);
        when(reviewService.create(any())).thenReturn(savedReview);

        mockMvc.perform(post(Paths.REVIEW_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
  {
    "reviewID": 1,
    "headline": "This is the most funniest book i have read",
    "body": "Like every great lasagna, Garfield was born in the kitchen of an Italian restaurant on a winter's night in 1978, while outside snow fell like grated Parmesan cheese. He weighed five pounds, six ounces at birth—that's big for a kitten!—and right from the start showed a passion for Italian food",
    "stars": 4.50,
    "isbn": "9780545437643",
    "userID": 5
  }
        """))
    .andExpect(status().isCreated())
    .andExpect((jsonPath("$.reviewID").value(1)))
    .andExpect((jsonPath("$.headline").value("This is the most funniest book i have read")));
     }



                @Test 
     void createReportsEveryEmptyField() throws Exception{
         mockMvc.perform(post(Paths.REVIEW_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content(Misc.EMPTY_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.headline").value(ValidationMessages.HEADLINE_REQUIRED))
    .andExpect(jsonPath("$.errors.isbn").value(ValidationMessages.ISBN_REQUIRED))
    .andExpect(jsonPath("$.errors.stars").value(ValidationMessages.STARS_REQUIRED))
    .andExpect(jsonPath("$.errors.userID").value(ValidationMessages.USER_ID_REQUIRED));
    

    verifyNoInteractions(reviewService);
     }

               @Test 
     void createReportsEveryInvalidFieldAtOnceVariantOne() throws Exception{
         mockMvc.perform(post(Paths.REVIEW_PATH)
     .contentType(MediaType.APPLICATION_JSON)
    .content("""
  {
  "headline": "I enjoyed Garfield as a child, and owned several of his books. Jim Davis improved the looks of the characters over the years, and that was my first exposure to the quality of the artwork visibly improving in later books compared to earlier ones.",
    "body": "I stumbled on a selection of Garfield paperbacks someone had dropped off at Goodwill the other day and was hit with a wave of nostalgia. I loved those books as a kid and devoured them at my local public library. I spent several minutes flipping through them and reminiscing. Now, I haven't touched the comic in many years and have been under the assumption that compared to the all-time greats that are darlings of this sub (i.e., Calvin and Hobbes, Gasoline Alley, Krazy Kat, Pogo, Popeye), Davis is soulless, a sellout, and frankly a hack who's had ghost writers for years, laughing all the way to the bank with his merchandising deals. Garfield seems like he's more of a cultural icon than a comic strip at this point. However, that assumption may be wildly off-base.",
    "stars": 6.56,
    "isbn": "1280545432483",
    "userID": -5
}
        """))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.headline").value(ValidationMessages.HEADLINE_LENGHT_SIZE))
    .andExpect(jsonPath("$.errors.body").value(ValidationMessages.BODY_SIZE_VALID))
    .andExpect(jsonPath("$.errors.isbn").value(ValidationMessages.ISBN_VALID_FORMAT))
    .andExpect(jsonPath("$.errors.userID").value(ValidationMessages.USER_ID_VALID));   

    verifyNoInteractions(reviewService);
     }

       @Test 
     void getByIdReturns400WhenTheIdIsNotANumber() throws Exception{
         mockMvc.perform(get(Paths.COMBINE_PATH(Paths.REVIEW_PATH, Paths.NOT_A_NUMBER)))
    .andExpect(status().isBadRequest())
    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
    .andExpect(jsonPath("$.detail").value("Failed to convert 'reviewID' with value: 'G'"));

    verifyNoInteractions(reviewService);
     }

  @Test
    void getByIdReturns404ForAMissingReview() throws Exception {
        when(reviewService.getById(Misc.INVALID_NUMBER)).thenThrow(new NotFoundException(ExceptionMessages.GIVEN_REVIEW_NOT_FOUND.formatted(Misc.INVALID_NUMBER)));

        mockMvc.perform(get(Paths.COMBINE_PATH(Paths.REVIEW_PATH, Paths.INVALID_NUMBER_STRING)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value(Misc.NOT_FOUND))
                .andExpect(jsonPath("$.detail").value(ExceptionMessages.GIVEN_REVIEW_NOT_FOUND.formatted(Misc.INVALID_NUMBER)));
    }
     @Test
    void deleteReturns204AndPassesTheIdToTheService() throws Exception {
        mockMvc.perform(delete("/api/reviews/1"))
                .andExpect(status().isNoContent());

        verify(reviewService).delete(1L);
    }

    


}