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

import java.time.LocalDateTime;
import org.springframework.test.web.servlet.MockMvc;

import com.books.spring.DTOs.RentalDTO;
import com.books.spring.Misc.BooksJavaConstants.*;
import com.books.spring.Models.Rental;
import com.books.spring.Services.RentalService;

@WebMvcTest(RentalController.class)
class RentalControllerTest {
     @Autowired 
     private MockMvc mockMvc;

     @MockitoBean 
     private RentalService rentalService;

                     @Test 
     void createReturns201AndSavedRental() throws Exception{
        RentalDTO savedRental = rental(1L,5L, "9780345287793",LocalDateTime.of(2026, 11, 30, 14, 04, 1), LocalDateTime.of(
            2026, 12, 30, 14, 04, 1),null,Rental.RentalStatus.Active,false);
        when(rentalService.create(any())).thenReturn(savedRental);

        mockMvc.perform(post(Paths.RENTAL_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
 {
  
  "rentalID": 1,
  "userID": 5,
  "isbn": "9780345287793",
  "rentedAt": "2026-11-30T14:04:01",
  "dueAt": "2026-12-30T14:04:01",
  "returnedAt": null,
  "status": "Active",
  "isDeleted": false
}
        """))
    .andExpect(status().isCreated())
    .andExpect((jsonPath("$.rentalID").value(1)))
    .andExpect((jsonPath("$.userID").value(5)))
    .andExpect((jsonPath("$.isbn").value("9780345287793")));
     }

                     @Test 
     void createReportsEveryEmptyField() throws Exception{
    mockMvc.perform(post(Paths.RENTAL_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content(Misc.EMPTY_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.userID").value(ValidationMessages.USER_ID_REQUIRED))
    .andExpect(jsonPath("$.errors.isbn").value(ValidationMessages.ISBN_REQUIRED));
    verifyNoInteractions(rentalService);
     }

             @Test 
     void createReportsEveryInvalidFieldAtOnceVariantOne() throws Exception{
         mockMvc.perform(post(Paths.RENTAL_PATH)
     .contentType(MediaType.APPLICATION_JSON)
    .content("""
 {
  "rentalID": 1,
  "userID": -5,
  "isbn": "220345287793"
    }
        """))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.userID").value(ValidationMessages.USER_ID_VALID))
    .andExpect(jsonPath("$.errors.isbn").value(ValidationMessages.ISBN_VALID_FORMAT));

    verifyNoInteractions(rentalService);
     }


           @Test
        void deleteReturns204AndPassesTheIdToTheService() throws Exception {
        mockMvc.perform(delete("/api/rentals/1"))
                .andExpect(status().isNoContent());

        verify(rentalService).delete(1L);
    }

    
           @Test
        void returnSetsTheReturnedAtToTheCurrentTimeAndSetsStatusToRetuned() throws Exception {
        RentalDTO savedRental = rental(1L,5L, "9780345287793",LocalDateTime.of(2026, 11, 30, 14, 04, 1), LocalDateTime.of(
            2026, 12, 30, 14, 04, 1), LocalDateTime.of(
            2026, 12, 12, 14, 04, 1),Rental.RentalStatus.Returned,false);
        when(rentalService.returnBook(1L)).thenReturn(savedRental);
        mockMvc.perform(put("/api/rentals/1/return"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnedAt").value("2026-12-12T14:04:01"))
                .andExpect(jsonPath("$.status").value("Returned"));

    }

     
             private static RentalDTO rental(Long rentalID,Long userID, String isbn,LocalDateTime rentedAt,LocalDateTime dueAt,LocalDateTime returnedAt,Rental.RentalStatus status, Boolean isDeleted) {
        return new RentalDTO(rentalID,userID, isbn,rentedAt,dueAt,returnedAt,status,isDeleted);
             }
}