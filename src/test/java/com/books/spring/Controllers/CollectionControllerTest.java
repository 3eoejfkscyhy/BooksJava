package com.books.spring.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.*;

import java.util.*;
import org.springframework.test.web.servlet.MockMvc;
import com.books.spring.DTOs.CollectionDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Misc.BooksJavaConstants.*;
import com.books.spring.Services.CollectionService;

@WebMvcTest(CollectionController.class)
class CollectionControllerTest {
     @Autowired 
     private MockMvc mockMvc;

     @MockitoBean 
     private CollectionService collectionService;

         @Test
    void getAllReturnsTheCollectionsAsJson() throws Exception {
        when(collectionService.getAll()).thenReturn(List.of(collection(1L, "My K00L b00KS!" ,7L , new String[]{"9780345287793","9780545287793"}),
                                                   collection(2L, "Harry Potter books" ,20L , new String[]{"9780545287693"})));

        mockMvc.perform(get(Paths.COLLECTION_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].collectionName").value("My K00L b00KS!"))
                .andExpect(jsonPath("$[1].collectionName").value("Harry Potter books"));
    }

    @Test 
     void createReturns201AndSavedCollection() throws Exception{
        CollectionDTO savedCollection = collection(1L, "My K00L b00KS!" ,7L , new String[]{"9780345287793","9780545287793"});
        when(collectionService.create(any())).thenReturn(savedCollection);

        mockMvc.perform(post(Paths.COLLECTION_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
    {
    "collectionID": 1,
    "collectionName": "My K00L b00KS!",
    "userID": 7,
    "isbns": [
      "9780345287793","9780545287793"
    ]
  }
        """))
    .andExpect(status().isCreated())
    .andExpect((jsonPath("$.collectionID").value(1)))
    .andExpect((jsonPath("$.collectionName").value("My K00L b00KS!")));
     }

     @Test 
     void createReportsEveryEmptyField() throws Exception{
         mockMvc.perform(post(Paths.COLLECTION_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content(Misc.EMPTY_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.collectionName").value(ValidationMessages.COLLECTION_NAME_REQUIRED))
    .andExpect(jsonPath("$.errors.userID").value(ValidationMessages.USER_ID_REQUIRED));

    verifyNoInteractions(collectionService);
     }


      @Test 
     void createReportsEveryInvalidFieldAtOnce() throws Exception{
         mockMvc.perform(post(Paths.COLLECTION_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
     {
    "collectionName": "Biggs Calderhead. Leading tennis hustler. Eastern seaboard. Once won $12,000 playing with one foot tied to a concrete block. Started tennis at age three. Plays tennis like Spassky plays chess. Says: “A smart tennis player can beat a strong tennis player five games out of seven.” This game shows what he means.",
    "userID": -14,
    "isbns": [
    ]
  }
        """))
    .andExpect(status().isBadRequest())
     .andExpect(jsonPath("$.errors.collectionName").value(ValidationMessages.COLLECTION_NAME_SIZE_VALID))
    .andExpect(jsonPath("$.errors.userID").value(ValidationMessages.USER_ID_VALID));

    verifyNoInteractions(collectionService);
     }

        @Test 
     void getByIDReturns400WhenTheIdIsNotANumber() throws Exception{
     mockMvc.perform(get(Paths.COMBINE_PATH(Paths.COLLECTION_PATH, Paths.NOT_A_NUMBER)))
    .andExpect(status().isBadRequest())
    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
    .andExpect(jsonPath("$.detail").value("Failed to convert 'collectionID' with value: 'G'"));

    verifyNoInteractions(collectionService);
     }

     @Test
    void getByIDReturns404ForAMissingCollection() throws Exception {
        when(collectionService.getByID(Misc.INVALID_NUMBER)).thenThrow(new NotFoundException(ExceptionMessages.GIVEN_COLLECTION_NOT_FOUND.formatted(Misc.INVALID_NUMBER)));

        mockMvc.perform(get(Paths.COMBINE_PATH(Paths.COLLECTION_PATH, Paths.INVALID_NUMBER_STRING)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value(Misc.NOT_FOUND))
                .andExpect(jsonPath("$.detail").value(ExceptionMessages.GIVEN_COLLECTION_NOT_FOUND.formatted(Misc.INVALID_NUMBER)));
    }
     @Test
    void deleteReturns204AndPassesTheIdToTheService() throws Exception {
        mockMvc.perform(delete("/api/collections/1"))
                .andExpect(status().isNoContent());

        verify(collectionService).delete(1L);
    }



     private static CollectionDTO collection(Long collectionID, String collectionName,Long userID, String[] isbnList) {
        Set<String> isbns = new HashSet<>();
        for (String isbn : isbnList) {
            isbns.add(isbn);
        }
        return new CollectionDTO(collectionID, collectionName, userID, isbns);

        
    }
}