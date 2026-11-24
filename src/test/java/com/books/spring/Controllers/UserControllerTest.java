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
import static  org.assertj.core.api.Assertions.assertThat;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.test.web.servlet.MockMvc;

import com.books.spring.DTOs.UserDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Misc.BooksJavaConstants.*;
import com.books.spring.Services.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {
     @Autowired 
     private MockMvc mockMvc;

     @MockitoBean 
     private UserService userService;
           @Test
    void getAllReturnsTheUsersAsJson() throws Exception {
        when(userService.getAll()).thenReturn(List.of(user(1L, "TDFJ_34", "TDFJ34@Yahoo.com", "GMndh937","Reading Moby Dick!",
                                     new BigDecimal(173.98),new Long[]{3L,8L},new Long[]{8L,29L}),
                                                   user(2L, "Lonsoz92", "LonLSoz@Gmail.com", "Gucdbudvb28","Sup?",
                                     new BigDecimal(13.48),new Long[]{9L},new Long[]{34L,35L})));

        mockMvc.perform(get(Paths.USER_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("TDFJ_34"))
                .andExpect(jsonPath("$[1].username").value("Lonsoz92"));
    }

            @Test 
     void createReturns201AndSavedUser() throws Exception{
        UserDTO savedUser = user(1L, "TDFJ_34", "TDFJ34@Yahoo.com", "GMndh937","Reading Moby Dick!",
                                     new BigDecimal(173.98),new Long[]{3L,8L},new Long[]{8L,29L});
        when(userService.create(any())).thenReturn(savedUser);

        mockMvc.perform(post(Paths.USER_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content("""
   {
    "userID": 1,
    "username": "TDFJ_34",
    "email": "TDFJ34@Yahoo.com",
    "password": "GMndh937",
    "status": "Reading Moby Dick!",
    "reviewIDs": [
     3,8
    ],
    "collectionIDs": [
      8,29
    ]
  }
        """))
    .andExpect(status().isCreated())
    .andExpect((jsonPath("$.userID").value(1)))
    .andExpect((jsonPath("$.username").value("TDFJ_34")))
    .andExpect((jsonPath("$.email").value("TDFJ34@Yahoo.com")));
     }

                     @Test 
     void createReportsEveryEmptyField() throws Exception{
    mockMvc.perform(post(Paths.USER_PATH)
    .contentType(MediaType.APPLICATION_JSON)
    .content(Misc.EMPTY_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.username").value(ValidationMessages.USERNAME_REQUIRED))
    .andExpect(jsonPath("$.errors.email").value(ValidationMessages.EMAIL_REQUIRED))
    .andExpect(jsonPath("$.errors.password").value(ValidationMessages.PASSWORD_REQUIRED));
    verifyNoInteractions(userService);
     }


        @Test 
     void createReportsEveryInvalidFieldAtOnceVariantOne() throws Exception{
         mockMvc.perform(post(Paths.USER_PATH)
     .contentType(MediaType.APPLICATION_JSON)
    .content("""
  {
    "userID": 1,
    "username": "Bruce Willis, Milla Jovovich, and Gary Oldman",
    "email": "https://www.youtube.com/",
    "password": "хд",
    "status": "most of the words are really hard to understand. I've been on drum & bass party once (DJ Andy C and MC GQ @ Spirit Of Burgas, Bulgaria 2010) but there I didn't have any problem with understanding MC GQ's lyrics. But the MSX FM lyrics are hard to listen."
  }
        """))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.errors.username").value(ValidationMessages.USERNAME_LENGHT_SIZE))
    .andExpect(jsonPath("$.errors.email").value(ValidationMessages.EMAIL_VALID))
    .andExpect(jsonPath("$.errors.password").value(containsString(ValidationMessages.PASSWORD_SIZE_VALID)))
    .andExpect(jsonPath("$.errors.password").value(containsString(ValidationMessages.PASSWORD_VALID_FORMAT)))
    .andExpect(jsonPath("$.errors.status").value(ValidationMessages.STATUS_SIZE_VALID)); 

    verifyNoInteractions(userService);
     }

     @Test 
     void getByIdReturns400WhenTheIdIsNotANumber() throws Exception{
         mockMvc.perform(get(Paths.COMBINE_PATH(Paths.USER_PATH, Paths.NOT_A_NUMBER)))
    .andExpect(status().isBadRequest())
    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
    .andExpect(jsonPath("$.detail").value("Failed to convert 'userID' with value: 'G'"));

    verifyNoInteractions(userService);
     }

       @Test
    void getByIdReturns404ForAMissingUser() throws Exception {
        when(userService.getByUserID(Misc.INVALID_NUMBER)).thenThrow(new NotFoundException(ExceptionMessages.GIVEN_USER_NOT_FOUND.formatted(Misc.INVALID_NUMBER)));

        mockMvc.perform(get(Paths.COMBINE_PATH(Paths.USER_PATH, Paths.INVALID_NUMBER_STRING)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value(Misc.NOT_FOUND))
                .andExpect(jsonPath("$.detail").value(ExceptionMessages.GIVEN_USER_NOT_FOUND.formatted(Misc.INVALID_NUMBER)));
    }

     @Test
    void deleteReturns204AndPassesTheIdToTheService() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        verify(userService).delete(1L);
    }

               @Test
    void givenInvalidStarsShouldGiveConstraintViolation() throws Exception {
       Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
          UserDTO user = user(1L, "TDFJ_34", "TDFJ34@Yahoo.com", "GMndh937","Reading Moby Dick!",
                                     new BigDecimal(221212122.932),new Long[]{3L,8L},new Long[]{8L,29L});
     Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
     assertThat(violations).hasSize(1);
     assertThat(violations).extracting("message").contains("numeric value out of bounds (<6 digits>.<2 digits> expected)");
    }

    @Test
    void decryptPasswordDecryptsEncryptedPassword() throws Exception {
        when(userService.decryptPass(1L)).thenReturn("The decrypted password for user with the ID 1 is W1deEyedMike");
        mockMvc.perform(get("/api/users/1/decrypt-password"))
        .andExpect(status().isOk())
        .andExpect(content().string("The decrypted password for user with the ID 1 is W1deEyedMike"));
    }

    @Test
    void addingNegativeFundsThrowsBadRequestException() throws Exception {
       mockMvc.perform(post("/api/users/1/balance").param("amount", "-12"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value(ExceptionMessages.DEPOSIT_MUST_BE_BIGGER_THAN_ZERO));
                 verifyNoInteractions(userService);
    } 



         private static UserDTO user(Long userID, String username,String email, String password, String status,BigDecimal balance,Long[] reviewIDs,Long[] collectionIDs) {

        Set<Long> reviewIds = new HashSet<>();
        for (Long reviewId : reviewIDs) {
            reviewIds.add(reviewId);
        }
         Set<Long> collectionIds = new HashSet<>();
        for (Long collectionId : collectionIDs) {
            collectionIds.add(collectionId);
        }
        return new UserDTO(userID, username,email,password,status,balance,reviewIds,collectionIds);
    }
}
