package com.books.spring.Services;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;


import com.books.spring.DTOs.BookDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Mappers.BookMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Misc.BooksJavaFunctions;
import com.books.spring.Models.Book;
import com.books.spring.Repositories.BookRepository;



@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
     private BookRepository bookRepository;
     @Mock
     private BookMapper bookMapper;
     @InjectMocks 
     private BookService bookService;


@Test
void getByISBNReturnsMappedBook()
{
    Book THLatT = Book.builder()
  .isbn("9870230748392")
  .title("Tierry Henry: Lonely at the Top")
  .bookLength(352L)
  .language("English")
  .price(new BigDecimal("2.00"))
          .bookType(Book.BookType.EBook)
          .rentalPrice(new BigDecimal("1.00"))
          .build();

    BookDTO THLatTDTO = new BookDTO(null, null, null, null, null, null, null, null, null, null, null, null, null);
    when(bookRepository.findById("9870230748392")).thenReturn(Optional.of(THLatT));
    when(bookMapper.toDto(THLatT)).thenReturn(THLatTDTO);

    BookDTO result = bookService.getByIsbn("9870230748392");
    assertThat(result).isSameAs(THLatTDTO);
}

@Test
void getByIsbnThrowsNotFoundForAMissingBook()
{
    when(bookRepository.findById("9870230748333")).thenReturn(Optional.empty());

    assertThatThrownBy(()->bookService.getByIsbn("9870230748333")).isInstanceOf((NotFoundException.class)).hasMessage(ExceptionMessages.GIVEN_BOOK_NOT_FOUND.formatted("9870230748333"));
}
       @Test
    void givenInvalidPricesShouldGiveConstraintViolation() throws Exception {
       Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        BookDTO book = BooksJavaFunctions.book("9780345287793",
                                                            "Garfield at Large: His First Book",
                                                            96L,
                                                            "English",
                                                            Book.BookType.EBook,
                                                            new BigDecimal("110292.9933"),
                                                            new BigDecimal("43994.9119"),
                                                        new Long[]{4L},new Long[]{1L,17L},new Long[]{3L,8L},new Long[]{8L,29L},null,null);
     Set<ConstraintViolation<BookDTO>> violations = validator.validate(book);
     assertThat(violations).hasSize(2);
     assertThat(violations).extracting("message").contains("numeric value out of bounds (<3 digits>.<2 digits> expected)");
    }
}


