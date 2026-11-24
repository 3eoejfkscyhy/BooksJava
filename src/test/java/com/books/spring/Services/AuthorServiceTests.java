package com.books.spring.Services;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import com.books.spring.DTOs.AuthorDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Mappers.AuthorMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Models.Author;
import com.books.spring.Repositories.AuthorRepository;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTests {
    @Mock
     private AuthorRepository authorRepository;
     @Mock
     private AuthorMapper authorMapper;
     @InjectMocks 
     private AuthorService authorService;


@Test
void getByIdReturnsMappedAuthor()
{
    Author Cixin = Author.builder().authorID(1L).firstName("Cixin").lastName("Liu").DOB(LocalDate.of(1963, 6, 23)).booksWritten(9).build();
    AuthorDTO CixinDTO = new AuthorDTO(null, null, null, null, null, null);
    when(authorRepository.findByAuthorID(1L)).thenReturn(Optional.of(Cixin));
    when(authorMapper.toDto(Cixin)).thenReturn(CixinDTO);

    AuthorDTO result = authorService.getByID(1L);
    assertThat(result).isSameAs(CixinDTO);
}

@Test
void getByIdThrowsNotFoundForAMissingAuthor()
{
    when(authorRepository.findByAuthorID(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(()->authorService.getByID(99L)).isInstanceOf((NotFoundException.class)).hasMessage(ExceptionMessages.GIVEN_AUTHOR_NOT_FOUND.formatted(99L));
}

@Test
void createSavesTheAuthorWithourTheIdSentByTheClient()
{
    AuthorDTO request = authorDTO(7L,"Cixin");
     Author Cixin = Author.builder().authorID(7L).firstName("Cixin").lastName("Liu").DOB(LocalDate.of(1963, 6, 23)).booksWritten(9).build();
     AuthorDTO response = new AuthorDTO(null, null, null, null, null,null);
     when(authorMapper.toEntity(request)).thenReturn((Cixin));
     when(authorRepository.save(Cixin)).thenReturn((Cixin));
     when(authorMapper.toDto(Cixin)).thenReturn((response));

     AuthorDTO result = authorService.create(request);
     assertThat(result).isSameAs(response); 
     assertThat(Cixin.getAuthorID()).isNull();
}

@Test 
void updateAcceptsTheAuthorsOwnID()
{
    Author Cixin = Author.builder().authorID(1L).firstName("Cixin").lastName("Liu").DOB(LocalDate.of(1963, 6, 23)).booksWritten(9).build();
    when(authorRepository.findByAuthorID(1L)).thenReturn(Optional.of(Cixin));

    authorService.update(1L, authorDTO(1L,"Cixin"));
    verify(authorRepository,never()).existsByAuthorID(any());
    verify(authorRepository).save(Cixin);
}

    private static AuthorDTO authorDTO(Long authorID,String FirstName)
    {
       return new AuthorDTO(authorID, FirstName, null, null, null, null);
    }
}


