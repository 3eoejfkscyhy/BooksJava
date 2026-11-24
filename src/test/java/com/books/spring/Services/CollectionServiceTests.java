package com.books.spring.Services;

import static org.mockito.Mockito.when;

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


import com.books.spring.DTOs.CollectionDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Mappers.CollectionMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Models.Collection;
import com.books.spring.Repositories.CollectionRepository;
import com.books.spring.Repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class CollectionServiceTests {
      @Mock
       private CollectionRepository collectionRepository;
        @Mock
     private UserRepository userRepository;
     @Mock
     private CollectionMapper collectionMapper;
     @InjectMocks 
     private CollectionService collectionService;


@Test
void getByIdReturnsMappedCollection()
{
    Collection UC = Collection.builder().collectionID(1L).collectionName("Unnamed Collection").build();
    CollectionDTO UCDTO = new CollectionDTO(null, null, null, null);
    when(collectionRepository.findByCollectionID(1L)).thenReturn(Optional.of(UC));
    when(collectionMapper.toDto(UC)).thenReturn(UCDTO);

    CollectionDTO result = collectionService.getByID(1L);
    assertThat(result).isSameAs(UCDTO);
}


@Test
void getByIdThrowsNotFoundForAMissingCollection()
{
    when(collectionRepository.findByCollectionID(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(()->collectionService.getByID(99L)).isInstanceOf((NotFoundException.class)).hasMessage(ExceptionMessages.GIVEN_COLLECTION_NOT_FOUND.formatted(99L));
}

@Test
void createSavesTheCollectionWithourTheIdSentByTheClient()
{
    CollectionDTO request = collectionDTO(7L,"Unnamed Collection");
    Collection UC = Collection.builder().collectionID(7L).collectionName("Unnamed Collection").build();
     CollectionDTO response = new CollectionDTO(null, null, null,null);
     when(collectionMapper.toEntity(request)).thenReturn((UC));
     when(collectionRepository.save(UC)).thenReturn((UC));
     when(collectionMapper.toDto(UC)).thenReturn((response));

     CollectionDTO result = collectionService.create(request);
     assertThat(result).isSameAs(response); 
     assertThat(UC.getCollectionID()).isNull(); 
}

@Test 
void updateAcceptsTheCollectionsOwnID()
{
    Collection UC = Collection.builder().collectionID(1L).collectionName("Unnamed Collection").build();
    when(collectionRepository.findByCollectionID(1L)).thenReturn(Optional.of(UC));

    collectionService.update(1L, collectionDTO(1L,"Unnamed Collection"));
    verify(collectionRepository,never()).existsByCollectionID(any());
    verify(collectionRepository).save(UC);
}

    private static CollectionDTO collectionDTO(Long collectionID,String CollectionName)
    {
       return new CollectionDTO(collectionID, CollectionName,null, null);
    }
}