package com.books.spring.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.*;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


import com.books.spring.Misc.BooksJavaFunctions;
import org.springframework.test.web.servlet.MockMvc;
import com.books.spring.DTOs.BookDTO;
import com.books.spring.DTOs.FreePeriodDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Misc.BooksJavaConstants.*;
import com.books.spring.Models.Book;
import com.books.spring.Services.BookService;



@WebMvcTest(BookController.class)
class BookControllerTest {


     @Autowired 
     private MockMvc mockMvc;

     @MockitoBean 
     private BookService bookService;

     @Test
    void getAllReturnsTheBooksAsJson() throws Exception {
        when(bookService.getAll()).thenReturn(List.of(BooksJavaFunctions.book("9780345287793",
                                                            "Garfield at Large: His First Book",
                                                            96L,
                                                            "English",
                                                            Book.BookType.EBook,
                                                            new BigDecimal("12.99"),
                                                            new BigDecimal("4.99"),
                                                        new Long[]{4L},new Long[]{1L,17L},new Long[]{3L,8L},new Long[]{8L,29L},null,null),
                                                   BooksJavaFunctions.book("9780593356418",
                                                            "Be a Work in Progress",
                                                            184L,
                                                            "English",
                                                            Book.BookType.EBook,
                                                            new BigDecimal("25.99"),
                                                            new BigDecimal("8.95"),
                                                        new Long[]{8L},new Long[]{3L,30L},new Long[]{3L,40L},new Long[]{8L},null,null)));

        mockMvc.perform(get(Paths.BOOK_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].isbn").value("9780345287793"))
                .andExpect(jsonPath("$[1].isbn").value("9780593356418"));
    }


     @Test 
     void createReturns201AndSavedBook() throws Exception{
        BookDTO savedBook = BooksJavaFunctions.book("9780345287793",
                                                            "Garfield at Large: His First Book",
                                                            96L,
                                                            "English",
                                                            Book.BookType.EBook,
                                                            new BigDecimal("12.99"),
                                                            new BigDecimal("4.99"),
                                                        new Long[]{4L},new Long[]{1L,17L},new Long[]{3L,8L},new Long[]{8L,29L},null,null);
        when(bookService.create(any())).thenReturn(savedBook);

        mockMvc.perform(post(Paths.BOOK_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
    {
    "isbn": "9780345287793",
    "title": "Garfield at Large: His First Book",
    "bookLength": 96,
    "language": "English",
    "bookType": "EBook",
    "price": 25.99,
    "rentalPrice": 8.95,
    "authorIDs": [
      4
    ],
    "reviewIDs": [
      1,17
    ],
    "genreIDs": [
      3,8
    ],
    "collectionIDs": [
      8,29
    ]
  }
        """))
    .andExpect(status().isCreated())
    .andExpect((jsonPath("$.isbn").value("9780345287793")))
    .andExpect((jsonPath("$.title").value("Garfield at Large: His First Book")));
     }

     @Test 
     void createReportsEveryEmptyField() throws Exception{
         mockMvc.perform(post(Paths.BOOK_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content(Misc.EMPTY_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.isbn").value(ValidationMessages.ISBN_REQUIRED))
    .andExpect(jsonPath("$.errors.title").value(ValidationMessages.BOOK_TITLE_REQUIRED))
    .andExpect(jsonPath("$.errors.bookType").value(ValidationMessages.BOOK_TYPE_REQUIRED))
    .andExpect(jsonPath("$.errors.price").value(ValidationMessages.PRICE_REQUIRED));
    

     verifyNoInteractions(bookService);
     }

      @Test 
     void createReportsEveryInvalidFieldAtOnce() throws Exception{
         mockMvc.perform(post(Paths.BOOK_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
     {
  "isbn": "6684961862953",
  "title": "",
  "bookLength": -67,
  "language": "κινέζικα",
  "bookType": "EBook",
  "price": -23.03,
  "rentalPrice": -49.05,
  "authorIDs": [
  ],
  "reviewIDs": [

  ],
  "genreIDs": [

  ],
  "collectionIDs": [

  ]
    }
        """))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.isbn").value(ValidationMessages.ISBN_VALID_FORMAT))
    .andExpect(jsonPath("$.errors.title").value(ValidationMessages.BOOK_TITLE_REQUIRED))
    .andExpect(jsonPath("$.errors.bookLength").value(ValidationMessages.BOOK_LENGHT_VALID))
    .andExpect(jsonPath("$.errors.language").value(ValidationMessages.LANGUAGE_MUST_BE_LATIN_LETTERS))
    .andExpect(jsonPath("$.errors.price").value(containsString(ValidationMessages.PRICE_POSITIVE)))
    .andExpect(jsonPath("$.errors.rentalPrice").value(containsString(ValidationMessages.RENTAL_PRICE_POSITIVE)));
    

    verifyNoInteractions(bookService);
     }

    @Test 
     void getByIdReturns409WhenTheIsbnIsNotANumber() throws Exception{
     mockMvc.perform(get(Paths.COMBINE_PATH(Paths.BOOK_PATH, Paths.NOT_A_NUMBER)))
    .andExpect(status().isConflict())
    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
    .andExpect(jsonPath("$.detail").value(ValidationMessages.ISBN_VALID_FORMAT));

    verifyNoInteractions(bookService);
     }

        @Test
    void getByIsbnReturns404ForAMissingBook() throws Exception {
        when(bookService.getByIsbn(Misc.ISBN_STRING)).thenThrow(new NotFoundException(ExceptionMessages.GIVEN_BOOK_NOT_FOUND.formatted(Misc.ISBN_STRING)));

        mockMvc.perform(get(Paths.COMBINE_PATH(Paths.BOOK_PATH, Paths.ISBN)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value(Misc.NOT_FOUND))
                .andExpect(jsonPath("$.detail").value(ExceptionMessages.GIVEN_BOOK_NOT_FOUND.formatted(Misc.ISBN_STRING)));
    }

     @Test
    void deleteReturns204AndPassesTheIsbnToTheService() throws Exception {
        mockMvc.perform(delete("/api/books/9780345287793"))
                .andExpect(status().isNoContent());

        verify(bookService).delete("9780345287793");
    }

 //Try in services to do these ones and fix this
    @Test 
     void putFreePeriodSetsAFreePeriod() throws Exception{
        FreePeriodDTO freePeriod = new FreePeriodDTO(LocalDateTime.of(
            2026, 11, 22, 14, 04, 1),LocalDateTime.of(
            2026, 11, 30, 14, 04, 1) );
         when(bookService.setFreePeriod("9780345287793",freePeriod)).thenReturn(BooksJavaFunctions.book("9780345287793",
                                                            "Garfield at Large: His First Book",
                                                            96L,
                                                            "English",
                                                            Book.BookType.EBook,
                                                            new BigDecimal("12.99"),
                                                            new BigDecimal("4.99"),
                                                        new Long[]{4L},new Long[]{1L,17L},new Long[]{3L,8L},new Long[]{8L,29L},LocalDateTime.of(
            2026, 11, 22, 14, 04, 1),LocalDateTime.of(
            2026, 11, 30, 14, 04, 1)));
       mockMvc.perform(put("/api/books/9780345287793/free-period")
              .contentType(MediaType.APPLICATION_JSON)
       .content("""
 {
  "freeFrom": "2026-11-22T14:04:01",
  "freeUntil": "2026-11-30T14:04:01"
}
        """))
       .andExpect(status().isNoContent());
     }

       @Test 
     void deleteFreePeriodDeletesAFreePeriod() throws Exception{
         when(bookService.removeFreePeriod("9780345287793")).thenReturn(BooksJavaFunctions.book("9780345287793",
                                                            "Garfield at Large: His First Book",
                                                            96L,
                                                            "English",
                                                            Book.BookType.EBook,
                                                            new BigDecimal("12.99"),
                                                            new BigDecimal("4.99"),
                                                        new Long[]{4L},new Long[]{1L,17L},new Long[]{3L,8L},new Long[]{8L,29L},null,null));
       mockMvc.perform(delete("/api/books/9780345287793/free-period")
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNoContent());
     }

}