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

import java.time.LocalDate;
import java.util.*;

import org.springframework.test.web.servlet.MockMvc;

import com.books.spring.DTOs.AuthorDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Misc.BooksJavaConstants.*;
import com.books.spring.Services.AuthorService;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {
     @Autowired 
     private MockMvc mockMvc;

     @MockitoBean 
     private AuthorService authorService;

    @Test
    void getAllReturnsTheAuthorsAsJson() throws Exception {
        when(authorService.getAll()).thenReturn(List.of(author(1L, "Jim","Davis",LocalDate.of(1945,7,28),65, new String[]{"9780345287793"}),
                                                   author(2L, "John","Cena",LocalDate.of(1977,4,23),10,new String[]{})));

        mockMvc.perform(get(Paths.AUTHOR_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Jim"))
                .andExpect(jsonPath("$[1].firstName").value("John"));
    }

     @Test 
     void createReturns201AndSavedAuthor() throws Exception{
        AuthorDTO savedAuthor = new AuthorDTO(1L, "Jim", null, null, 0, null);
        when(authorService.create(any())).thenReturn(savedAuthor);

        mockMvc.perform(post(Paths.AUTHOR_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
     {
        "firstName": "Jim",
        "lastName": "Davis",
        "DOB": "1945-07-28",
        "booksWritten": 65,
        "isbns": [
        "9780099894100"
        ]
      }
        """))
    .andExpect(status().isCreated())
    .andExpect((jsonPath("$.authorID").value(1)))
    .andExpect((jsonPath("$.firstName").value("Jim")));
     }


 @Test 
     void createReportsEveryEmptyField() throws Exception{
         mockMvc.perform(post(Paths.AUTHOR_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content(Misc.EMPTY_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.firstName").value(ValidationMessages.FIRST_NAME_REQUIRED))
    .andExpect(jsonPath("$.errors.DOB").value(ValidationMessages.AUTHOR_DOB_REQUIRED));

    verifyNoInteractions(authorService);
     }



     
     @Test 
     void createReportsEveryInvalidFieldAtOnce() throws Exception{
         mockMvc.perform(post(Paths.AUTHOR_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
     {
        "firstName": "G",
        "lastName": "Pneumonoultramicroscopicsilicovolcanoconiosis",
        "DOB": "2029-03-09",
        "booksWritten": -120,
        "isbns": [
        ]
      }
        """))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.firstName").value(ValidationMessages.FIRST_NAME_LENGHT_SIZE))
    .andExpect(jsonPath("$.errors.lastName").value(ValidationMessages.LAST_NAME_LENGHT_SIZE))
    .andExpect(jsonPath("$.errors.DOB").value(ValidationMessages.AUTHOR_DOB_IN_PAST))
    .andExpect(jsonPath("$.errors.booksWritten").value(ValidationMessages.BOOKS_WRITTEN_AMOUNT));

    verifyNoInteractions(authorService);
     }

     

     @Test 
     void getByIdReturns400WhenTheIdIsNotANumber() throws Exception{
         mockMvc.perform(get(Paths.COMBINE_PATH(Paths.AUTHOR_PATH, Paths.NOT_A_NUMBER)))
    .andExpect(status().isBadRequest())
    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
    .andExpect(jsonPath("$.detail").value("Failed to convert 'authorID' with value: 'G'"));

    verifyNoInteractions(authorService);
     }

       @Test
    void getByIdReturns404ForAMissingAuthor() throws Exception {
        when(authorService.getByID(Misc.INVALID_NUMBER)).thenThrow(new NotFoundException(ExceptionMessages.GIVEN_AUTHOR_NOT_FOUND.formatted(Misc.INVALID_NUMBER)));

        mockMvc.perform(get(Paths.COMBINE_PATH(Paths.AUTHOR_PATH, Paths.INVALID_NUMBER_STRING)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value(Misc.NOT_FOUND))
                .andExpect(jsonPath("$.detail").value(ExceptionMessages.GIVEN_AUTHOR_NOT_FOUND.formatted(Misc.INVALID_NUMBER)));
    }
     @Test
    void deleteReturns204AndPassesTheIdToTheService() throws Exception {
        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());

        verify(authorService).delete(1L);
    }

      private static AuthorDTO author(Long authorID, String firstName,String lastName,LocalDate DOB,Integer booksWritten, String[] isbnList) {
        Set<String> isbns = new HashSet<>();
        for (String isbn : isbnList) {
            isbns.add(isbn);
        }
        return new AuthorDTO(authorID, firstName, lastName, DOB, booksWritten, isbns);
    }
}
