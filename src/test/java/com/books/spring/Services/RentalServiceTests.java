package com.books.spring.Services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import com.books.spring.DTOs.RentalDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Mappers.RentalMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Models.Rental;
import com.books.spring.Repositories.RentalRepository;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTests {
      @Mock
    private RentalRepository rentalRepository;
     @Mock
     private RentalMapper rentalMapper;
     @InjectMocks 
     private RentalService rentalService;


@Test
void getByIdReturnsMappedRental()
{
    Rental rental = Rental.builder().rentalID(1L).rentedAt(LocalDateTime.of(2026,1,1,1,1,1))
    .dueAt(LocalDateTime.of(2026,2,1,1,1,1)).build();
    RentalDTO rentalDTO = new RentalDTO(null, null, null, null, null, null, null, null);
    when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
    when(rentalMapper.toDto(rental)).thenReturn(rentalDTO);

    RentalDTO result = rentalService.getByID(1L);
    assertThat(result).isSameAs(rentalDTO);
}
    @Test
    void getAllLeavesOutSoftDeletedRentals() {
        Rental rental = Rental.builder().rentalID(1L).rentedAt(LocalDateTime.of(2026,1,1,1,1,1))
    .dueAt(LocalDateTime.of(2026,2,1,1,1,1)).build();
        Rental endedRental = Rental.builder().rentalID(1L).rentedAt(LocalDateTime.of(2026,1,1,1,1,1))
    .dueAt(LocalDateTime.of(2026,2,1,1,1,1)).isDeleted(true).build();
        RentalDTO rentalDto = new RentalDTO(null, null, null, null, null, null, null, null);
        when(rentalRepository.findAll()).thenReturn(List.of(rental, endedRental));
        when(rentalMapper.toDto(rental)).thenReturn(rentalDto);

        List<RentalDTO> result = rentalService.getAll();

        assertThat(result).containsExactly(rentalDto);
    }

      @Test
    void deleteOnlyMarksTheRentalAsDeleted() {
        Rental rented = Rental.builder().rentalID(1L).rentedAt(LocalDateTime.of(2026,1,1,1,1,1))
    .dueAt(LocalDateTime.of(2026,2,1,1,1,1)).build();
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rented));

        rentalService.delete(1L);

        assertThat(rented.getIsDeleted()).isTrue();
        verify(rentalRepository).save(rented);
        verify(rentalRepository, never()).delete(any());
    }


@Test
void getByIdThrowsNotFoundForAMissingRental()
{
    when(rentalRepository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(()->rentalService.getByID(99L)).isInstanceOf((NotFoundException.class)).hasMessage(ExceptionMessages.GIVEN_RENTAL_NOT_FOUND.formatted(99L));
}
}