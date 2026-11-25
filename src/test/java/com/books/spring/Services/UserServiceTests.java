package com.books.spring.Services;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import com.books.spring.DTOs.UserDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Mappers.UserMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Misc.BooksJavaFunctions;
import com.books.spring.Models.User;
import com.books.spring.Repositories.UserRepository;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
      @Mock
    private UserRepository userRepository;
     @Mock
     private UserMapper userMapper;
     @InjectMocks 
     private UserService userService;


         @Test
    void givenInvalidStarsShouldGiveConstraintViolation() throws Exception {
       Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
          UserDTO user = BooksJavaFunctions.user(1L, "TDFJ_34", "TDFJ34@Yahoo.com", "GMndh937","Reading Moby Dick!",
                                     new BigDecimal(221212122.932),new Long[]{3L,8L},new Long[]{8L,29L});
     Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
     assertThat(violations).hasSize(1);
     assertThat(violations).extracting("message").contains("numeric value out of bounds (<6 digits>.<2 digits> expected)");
    }


    @Test
void getByIdReturnsMappedUser()
{
        User Antony = User.builder()
     .username("AntonyReal")
     .email("FutBallG0at@yahoo.com")
     .password("RealDiaB3tis")
     .status("I am a smart dude, I can read the pitch")
     .balance(new BigDecimal(10.30))
     .build();
    UserDTO AntonyDTO = new UserDTO(null, null, null, null, null, null, null, null);
    when(userRepository.findByUserID(1L)).thenReturn(Optional.of(Antony));
    when(userMapper.toDto(Antony)).thenReturn(AntonyDTO);

    UserDTO result = userService.getByUserID(1L);
    assertThat(result).isSameAs(AntonyDTO);
}


@Test
void getByIdThrowsNotFoundForAMissingUser()
{
    when(userRepository.findByUserID(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(()->userService.getByUserID(99L)).isInstanceOf((NotFoundException.class)).hasMessage(ExceptionMessages.GIVEN_USER_NOT_FOUND.formatted(99L));
}
}