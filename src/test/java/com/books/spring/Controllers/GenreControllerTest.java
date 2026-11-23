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

import com.books.spring.DTOs.GenreDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Misc.BooksJavaConstants.*;
import com.books.spring.Services.GenreService;

@WebMvcTest(GenreController.class)
class GenreControllerTest {
     @Autowired 
     private MockMvc mockMvc;

     @MockitoBean 
     private GenreService genreService;

      @Test
    void getAllReturnsTheGenresAsJson() throws Exception {
        when(genreService.getAll()).thenReturn(List.of(genre(1L, "Horror", new String[]{"9780345288893","9780545287243"}),
                                                   genre(2L, "Jokebook" , new String[]{"9780545437643"})));

        mockMvc.perform(get(Paths.GENRE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].genreName").value("Horror"))
                .andExpect(jsonPath("$[1].genreName").value("Jokebook"));
    }


        @Test 
     void createReturns201AndSavedGenre() throws Exception{
        GenreDTO savedGenre = genre(1L, "Horror", new String[]{"9780345288893","9780545287243"});
        when(genreService.create(any())).thenReturn(savedGenre);

        mockMvc.perform(post(Paths.GENRE_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
  {
  "genreID": 1,
  "genreName": "Horror",
  "isbns": [
    "9780345288893","9780545287243"
  ]
}
        """))
    .andExpect(status().isCreated())
    .andExpect((jsonPath("$.genreID").value(1)))
    .andExpect((jsonPath("$.genreName").value("Horror")));
     }

           @Test 
     void createReportsEveryEmptyField() throws Exception{
         mockMvc.perform(post(Paths.GENRE_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content(Misc.EMPTY_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.genreName").value(ValidationMessages.GENRE_NAME_REQUIRED));

    verifyNoInteractions(genreService);
     }

          @Test 
     void createReportsEveryInvalidFieldAtOnce() throws Exception{
         mockMvc.perform(post(Paths.GENRE_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
  {
  "genreName": "A copypasta is a block of text copied and pasted to the Internet and social media. Copypasta containing controversial ideas or lengthy rants are often posted for humorous purposes, to provoke reactions from those unaware that the posted text is an Internet meme",
  "isbns": []
}
        """))
    .andExpect(status().isBadRequest())
     .andExpect(jsonPath("$.errors.genreName").value(ValidationMessages.GENRE_NAME_SIZE_VALID));     

    verifyNoInteractions(genreService);
     }


        @Test 
     void getByIdReturns400WhenTheIdIsNotANumber() throws Exception{
         mockMvc.perform(get(Paths.COMBINE_PATH(Paths.GENRE_PATH, Paths.NOT_A_NUMBER)))
    .andExpect(status().isBadRequest())
    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
    .andExpect(jsonPath("$.detail").value("Failed to convert 'genreID' with value: 'G'"));

    verifyNoInteractions(genreService);
     }

     @Test
    void getByIdReturns404ForAMissingGenre() throws Exception {
        when(genreService.getById(Misc.INVALID_NUMBER)).thenThrow(new NotFoundException(ExceptionMessages.GIVEN_GENRE_NOT_FOUND.formatted(Misc.INVALID_NUMBER)));

        mockMvc.perform(get(Paths.COMBINE_PATH(Paths.GENRE_PATH, Paths.INVALID_NUMBER_STRING)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value(Misc.NOT_FOUND))
                .andExpect(jsonPath("$.detail").value(ExceptionMessages.GIVEN_GENRE_NOT_FOUND.formatted(Misc.INVALID_NUMBER)));
    }
     @Test
    void deleteReturns204AndPassesTheIdToTheService() throws Exception {
        mockMvc.perform(delete("/api/genres/1"))
                .andExpect(status().isNoContent());

        verify(genreService).delete(1L);
    }



     private static GenreDTO genre(Long genreID, String genreName, String[] isbnList) {
        Set<String> isbns = new HashSet<>();
        for (String isbn : isbnList) {
            isbns.add(isbn);
        }
        return new GenreDTO(genreID, genreName, isbns);
    }
}