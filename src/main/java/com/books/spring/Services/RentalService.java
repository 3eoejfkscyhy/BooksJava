package com.books.spring.Services;

import com.books.spring.DTOs.RentalDTO;
import com.books.spring.DTOs.RentalRequestDTO;
import com.books.spring.Mappers.RentalMapper;
import com.books.spring.Models.Book;
import com.books.spring.Models.Rental;
import com.books.spring.Models.User;
import com.books.spring.Repositories.BookRepository;
import com.books.spring.Repositories.RentalRepository;
import com.books.spring.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        User user = findUser(dto.getUserID());
        Book book = findBook(dto.getIsbn());
        if(book.getBookType() != Book.BookType.EBook) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only Ebooks can be rented through the application");
        }
        if(user.getBalance().compareTo(book.getRentalPrice()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance to rent this book");
        }
        if(rentalRepository.existsActiveRental(user.getUserID(), book.getIsbn(), Rental.RentalStatus.Active)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This user has an active rental for this book already");
        }

        rental.setUser(user);
        rental.setBook(book);
        LocalDateTime rentedAt = LocalDateTime.now();
        rental.setRentedAt(rentedAt);
        rental.setDueAt(rentedAt.plusDays(20));
        rental.setStatus(Rental.RentalStatus.Active);
        user.setBalance(user.getBalance().subtract(book.getRentalPrice()));
        userRepository.save(user);
        return rentalMapper.toDto(rentalRepository.save(rental));
    }

    public RentalDTO returnBook(Long rentalID) {
        Rental rental = findRental(rentalID);

        if(rental.getStatus() == Rental.RentalStatus.Returned) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This rental has already been returned by this user");
        }

        rental.setReturnedAt(LocalDateTime.now());
        rental.setStatus(Rental.RentalStatus.Returned);
        return rentalMapper.toDto(rentalRepository.save(rental));
    }

    private User findUser(Long userID) {
        return userRepository.findByUserID(userID)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + userID + " could not be found"));
    }
    private Book findBook(String isbn) {
        return bookRepository.findById(isbn)
                .filter(book -> !book.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book " + isbn + " could not be found"));
    }
    private Rental findRental(Long rentalID) {
        return rentalRepository.findById(rentalID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rental " + rentalID + " could not be found"));
    }
}