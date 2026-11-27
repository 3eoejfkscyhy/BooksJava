package com.books.spring.Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import com.books.spring.DTOs.GenreDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Mappers.GenreMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Models.Genre;
import com.books.spring.Repositories.GenreRepository;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTests {
      @Mock
    private GenreRepository genreRepository;
     @Mock
     private GenreMapper genreMapper;
     @InjectMocks 
     private GenreService genreService;


@Test
void getByIdReturnsMappedGenre()
{
    Genre cookbook = Genre.builder().genreID(1L).genreName("Cookbook").build();
    GenreDTO CookbookDTO = new GenreDTO(null, null, null);
    when(genreRepository.findByGenreID(1L)).thenReturn(Optional.of(cookbook));
    when(genreMapper.toDto(cookbook)).thenReturn(CookbookDTO);

    GenreDTO result = genreService.getByID(1L);
    assertThat(result).isSameAs(CookbookDTO);
}


@Test
void getByIdThrowsNotFoundForAMissingGenre()
{
    when(genreRepository.findByGenreID(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(()->genreService.getByID(99L)).isInstanceOf((NotFoundException.class)).hasMessage(ExceptionMessages.GIVEN_GENRE_NOT_FOUND.formatted(99L));
}

@Test
void createSavesTheGenreWithourTheIdSentByTheClient()
{
    GenreDTO request = genreDTO(7L,"Cookbook");
    Genre Cookbook = Genre.builder().genreID(7L).genreName("Cookbook").build();
     GenreDTO response = new GenreDTO(null, null,null);
     when(genreMapper.toEntity(request)).thenReturn((Cookbook));
     when(genreRepository.save(Cookbook)).thenReturn((Cookbook));
     when(genreMapper.toDto(Cookbook)).thenReturn((response));

     GenreDTO result = genreService.create(request);
     assertThat(result).isSameAs(response); 
     assertThat(Cookbook.getGenreID()).isNull(); 
}

@Test 
void updateAcceptsTheGenresOwnID()
{
    Genre Cookbook = Genre.builder().genreID(1L).genreName("Cookbook").build();
    when(genreRepository.findByGenreID(1L)).thenReturn(Optional.of(Cookbook));

    genreService.update(1L, genreDTO(1L,"Cookbook"));
    verify(genreRepository,never()).existsByGenreName(any());
    verify(genreRepository).save(Cookbook);
}

    private static GenreDTO genreDTO(Long genreID,String genreName)
    {
       return new GenreDTO(genreID, genreName, null);
    }
}