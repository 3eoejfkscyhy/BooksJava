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
import java.util.*;

import org.springframework.test.web.servlet.MockMvc;

import com.books.spring.DTOs.UserBookDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Misc.BooksJavaConstants.*;
import com.books.spring.Misc.BooksJavaFunctions;
import com.books.spring.Models.UserBooks;
import com.books.spring.Services.UserBookService;

@WebMvcTest(UserBookController.class)
class UserBookControllerTest {
     @Autowired 
     private MockMvc mockMvc;

     @MockitoBean 
     private UserBookService userBookService;
           @Test
    void getByIdReturnsTheUserBooksAsJson() throws Exception {
        when(userBookService.getByUserID(1L)).thenReturn(List.of(userBook(1L, "9780345287793", UserBooks.Status.Rented, 78l,false,120L),
                                                   userBook(1L, "9870141019703", UserBooks.Status.Owned, 65l,false,290L)));

        mockMvc.perform(get("/api/user-books/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].isbn").value("9780345287793"))
                .andExpect(jsonPath("$[1].isbn").value("9870141019703"));
    }

                @Test 
     void createReturns201AndSavedUserBook() throws Exception{
        UserBookDTO savedUserBook = userBook(1L, "9780345287793", UserBooks.Status.Rented, 78l,false,120L);
        when(userBookService.create(any())).thenReturn(savedUserBook);

        mockMvc.perform(post(Paths.USERBOOK_PATH )
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
 {
  "userID": 1,
  "isbn": "9780345287793",
  "readingStatus": "Rented",
  "lastReadTo": 78,
  "isDeleted": false
}
        """))
    .andExpect(status().isCreated())
    .andExpect((jsonPath("$.userID").value(1)))
    .andExpect((jsonPath("$.isbn").value("9780345287793")))
    .andExpect((jsonPath("$.readingStatus").value("Rented")));
     }

                     @Test 
     void createReportsEveryEmptyField() throws Exception{
    mockMvc.perform(post(Paths.USERBOOK_PATH )
    .contentType(MediaType.APPLICATION_JSON)
    .content(Misc.EMPTY_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.userID").value(ValidationMessages.USER_ID_REQUIRED))
    .andExpect(jsonPath("$.errors.isbn").value(ValidationMessages.ISBN_REQUIRED))
    .andExpect(jsonPath("$.errors.lastReadTo").value(ValidationMessages.LAST_READ_PAGE_REQUIRED));
    verifyNoInteractions(userBookService);
     }

             @Test 
     void createReportsEveryInvalidFieldAtOnceVariantOne() throws Exception{
         mockMvc.perform(post(Paths.USERBOOK_PATH )
     .contentType(MediaType.APPLICATION_JSON)
    .content("""
 {
  "userID": -1,
  "isbn": "8120345287793",
  "readingStatus": "Rented",
  "lastReadTo": -278
    }
        """))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.userID").value(ValidationMessages.USER_ID_VALID))
    .andExpect(jsonPath("$.errors.isbn").value(ValidationMessages.ISBN_VALID_FORMAT))
    .andExpect(jsonPath("$.errors.lastReadTo").value(ValidationMessages.BOOK_LENGHT_VALID));

    verifyNoInteractions(userBookService);
     }


         @Test 
     void getByIdReturns400WhenTheIdIsNotANumber() throws Exception{
         mockMvc.perform(get(Paths.COMBINE_PATH("/api/user-books/user", Paths.NOT_A_NUMBER)))
    .andExpect(status().isBadRequest())
    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
    .andExpect(jsonPath("$.detail").value("Failed to convert 'userID' with value: 'G'"));

    verifyNoInteractions(userBookService);
     }

       @Test
    void getByIdReturns404ForAMissingUser() throws Exception {
        when(userBookService.getByUserID(Misc.INVALID_NUMBER)).thenThrow(new NotFoundException(ExceptionMessages.GIVEN_USER_NOT_FOUND.formatted(Misc.INVALID_NUMBER)));

        mockMvc.perform(get(Paths.COMBINE_PATH("/api/user-books/user", Paths.INVALID_NUMBER_STRING)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value(Misc.NOT_FOUND))
                .andExpect(jsonPath("$.detail").value(ExceptionMessages.GIVEN_USER_NOT_FOUND.formatted(Misc.INVALID_NUMBER)));
    }



       @Test
        void deleteReturns204AndPassesTheId_ISBNToTheService() throws Exception {
        mockMvc.perform(delete("/api/user-books/user/1/book/9780345287793"))
                .andExpect(status().isNoContent());

        verify(userBookService).delete(1L,"9780345287793");
    }


          @Test
        void purchasingTheGivenBookSetsTheStatusToOwned() throws Exception {
             when(userBookService.purchase(1L,"9780345287793")).thenReturn(userBook(1L, "9780345287793", UserBooks.Status.Owned, 0l,false,290L));
       mockMvc.perform(post("/api/user-books/user/1/book/9780345287793/purchase"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.readingStatus").value("Owned")));

    }




             private static UserBookDTO userBook(Long userID, String isbn,UserBooks.Status readingStatus,Long lastReadTo, Boolean isDeleted, Long BookLenght) {
        return new UserBookDTO(userID, isbn,readingStatus,lastReadTo,BooksJavaFunctions.percentageRead(lastReadTo,BookLenght),isDeleted);
    }


}