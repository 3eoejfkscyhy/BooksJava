package com.books.spring.Services;

import com.books.spring.DTOs.RentalDTO;
import com.books.spring.DTOs.RentalRequestDTO;
import com.books.spring.Mappers.RentalMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Misc.BooksJavaFunctions;
import com.books.spring.Models.*;
import com.books.spring.Repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.books.spring.Exception.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final RentalMapper rentalMapper;
      private final UserBookService userBookService;

    public List<RentalDTO> getAll() {
        return rentalRepository.findAll().stream()
                .map(rentalMapper::toDto)
                .toList();
    }

    public RentalDTO getByID(Long rentalID) {
        return rentalMapper.toDto(findRental(rentalID));
    }

    public RentalDTO create(RentalRequestDTO dto) {
        Rental rental = new Rental();
        User user = BooksJavaFunctions.findUser(userRepository,dto.userID());
        Book book = BooksJavaFunctions.findBook(bookRepository,dto.isbn());
        if(book.getBookType() != Book.BookType.EBook) {
            throw new BadRequestException(ExceptionMessages.ONLY_EBOOKS_ARE_RENTABLE);
        }
        if(user.getBalance().compareTo(book.getRentalPrice()) < 0) {
            throw new BadRequestException( ExceptionMessages.INSUFFICIENT_BALANCE_TO_RENT);
        }
        if(rentalRepository.existsActiveRental(user.getUserID(), book.getIsbn(), Rental.RentalStatus.Active)) {
            throw new ConflictException(ExceptionMessages.USER_HAS_BOOK_RENTED_ALREADY);
        }

        rental.setUser(user);
        rental.setBook(book);
        LocalDateTime rentedAt = LocalDateTime.now();
        rental.setRentedAt(rentedAt);
        rental.setDueAt(rentedAt.plusDays(20));
        rental.setStatus(Rental.RentalStatus.Active);
        UserBooks.Status previousStatus = userBookService.markRented(user.getUserID(), book.getIsbn());
        rental.setPreviousStatus(previousStatus);
        user.setBalance(user.getBalance().subtract(book.getRentalPrice()));
        userRepository.save(user);
        return rentalMapper.toDto(rentalRepository.save(rental));
    }

    public RentalDTO returnBook(Long rentalID) {
        Rental rental = findRental(rentalID);

        if(rental.getStatus() == Rental.RentalStatus.Returned) {
            throw new ConflictException(ExceptionMessages.USER_ALREADY_HAS_RETURNED_BOOK);
        }

        rental.setReturnedAt(LocalDateTime.now());
        rental.setStatus(Rental.RentalStatus.Returned);
        userBookService.restoreStatus(rental.getUser().getUserID(), rental.getBook().getIsbn(), rental.getPreviousStatus());
        return rentalMapper.toDto(rentalRepository.save(rental));
    }

    private Rental findRental(Long rentalID) {
        return rentalRepository.findById(rentalID)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.GIVEN_RENTAL_NOT_FOUND.formatted(rentalID)));
    }
}