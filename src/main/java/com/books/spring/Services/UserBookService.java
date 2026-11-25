package com.books.spring.Services;

import com.books.spring.DTOs.RentalDTO;
import com.books.spring.DTOs.RentalRequestDTO;
import com.books.spring.DTOs.UserBookDTO;
import com.books.spring.Mappers.RentalMapper;
import com.books.spring.Mappers.UserBookMapper;
import com.books.spring.Misc.BooksJavaConstants;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Misc.UserBookId;
import  com.books.spring.Misc.BooksJavaFunctions;
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
public class UserBookService {
    private final UserBookRepository userBookRepository;
    private final UserBookMapper userBookMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public List<UserBookDTO> getByUserID(Long userID) {
        return userBookRepository.findByUser_UserID(userID).stream()
                .filter(userBook->!userBook.getIsDeleted())
                .map(userBookMapper::toDto)
                .toList();
    }

    public UserBookDTO purchase(long userID, String isbn) {
        User user = BooksJavaFunctions.findUser(userRepository,userID);
        Book book = BooksJavaFunctions.findBook(bookRepository,isbn);

        if(book.getBookType() != Book.BookType.EBook) {
            throw new BadRequestException(ExceptionMessages.ONLY_EBOOK_CAN_BE_PURCHASED);
        }

        UserBookId id = new UserBookId(userID, isbn);
        UserBooks userBooks = userBookRepository.findById(id).orElse(null);

        if(userBooks != null && (userBooks.getReadingStatus() != UserBooks.Status.Wishlisted && userBooks.getReadingStatus() != UserBooks.Status.PreviouslyOwned) && !userBooks.getIsDeleted()) {
            throw new ConflictException(ExceptionMessages.BOOK_IS_ALREADY_OWNED_BY_USER);
        }
        if(user.getBalance().compareTo(book.getPrice()) < 0) {
            throw new BadRequestException(ExceptionMessages.INSUFFICIENT_BALANCE);
        }

        user.setBalance(user.getBalance().subtract(book.getPrice()));
        userRepository.save(user);

        if(userBooks == null) {
            userBooks = UserBooks.builder()
                    .user(user)
                    .book(book)
                    .readingStatus(UserBooks.Status.Owned)
                    .build();
        }
        else {
            userBooks.setIsDeleted(false);
            userBooks.setReadingStatus(UserBooks.Status.Owned);
        }

        return userBookMapper.toDto(userBookRepository.save(userBooks));
    }

    public UserBookDTO create(UserBookDTO dto) {
        User user = BooksJavaFunctions.findUser(userRepository,dto.userID());
        Book book = BooksJavaFunctions.findBook(bookRepository,dto.isbn());
        UserBookId id = new UserBookId(user.getUserID(), book.getIsbn());

        if(dto.readingStatus() == UserBooks.Status.Owned || dto.readingStatus() == UserBooks.Status.Rented) {
            throw new BadRequestException(ExceptionMessages.INVALID_READING_STATUS);
        }
        if(book.getBookType() == Book.BookType.EBook && dto.readingStatus() == UserBooks.Status.Read) {
            throw new BadRequestException(ExceptionMessages.INVALID_READING_STATUS);
        }

        UserBooks userBook = userBookRepository.findById(id).orElse(null);

        if(userBook != null && !userBook.getIsDeleted()) {
            throw new ConflictException(ExceptionMessages.USER_HAS_BOOK_IN_READING_LIST);
        }
         if(dto.lastReadTo() > book.getBookLength()) {
            throw new ConflictException(ExceptionMessages.GIVEN_READ_PAGE_MORE_THAN_AVAILABLE);
        }
         if(userBook == null) {
             userBook = UserBooks.builder()
                     .user(user)
                     .book(book)
                     .readingStatus(dto.readingStatus())
                     .lastReadTo(dto.lastReadTo())
                     .readingPercentage(BooksJavaFunctions.percentageRead(dto.lastReadTo(), book.getBookLength()))
                     .build();
         }
         else {
             userBook.setIsDeleted(false);
             userBook.setReadingStatus(dto.readingStatus());
             userBook.setLastReadTo(dto.lastReadTo());
             userBook.setReadingPercentage(BooksJavaFunctions.percentageRead(dto.lastReadTo(), book.getBookLength()));
         }

        return userBookMapper.toDto(userBookRepository.save(userBook));
    }

    public UserBookDTO update(Long userID, String isbn, Long lastReadTo, UserBooks.Status status) {
        UserBookId userBookID = new UserBookId(userID, isbn);
        Book book = BooksJavaFunctions.findBook(bookRepository, isbn);
        Long bookPages = book.getBookLength();

        UserBooks userBook = userBookRepository.findById(userBookID)
                .filter(relation -> !relation.getIsDeleted())
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.USER_BOOK_RELATIONSHIP_NOT_FOUND));
        UserBooks.Status currentStatus = userBook.getReadingStatus();

        if(book.getBookType() == Book.BookType.EBook) {
            if(currentStatus != UserBooks.Status.Owned && currentStatus != UserBooks.Status.Rented) {
                throw new ConflictException(ExceptionMessages.BOOK_IS_NOT_AVAILABLE_TO_USER);
            }
            if(status != currentStatus) {
                throw new BadRequestException(ExceptionMessages.INVALID_READING_STATUS);
            }
        }
        else {
            if (status != UserBooks.Status.Wishlisted && status != UserBooks.Status.PreviouslyOwned && status != UserBooks.Status.Read) {
                throw new BadRequestException(ExceptionMessages.INVALID_READING_STATUS);
            }
        }

        if(lastReadTo != null)
        {
                 if(lastReadTo>bookPages) {
            throw new ConflictException(ExceptionMessages.GIVEN_READ_PAGE_MORE_THAN_AVAILABLE);
        }
        if(status == UserBooks.Status.PreviouslyOwned || status == UserBooks.Status.Wishlisted) {
             throw new ConflictException(ExceptionMessages.CANT_UPDATE_WISHLISTED_PREVIOUSLY_OWNED_LAST_READ_PAGE);
        }
        userBook.setReadingStatus(status);
        userBook.setLastReadTo(lastReadTo);
        userBook.setReadingPercentage(BooksJavaFunctions.percentageRead(lastReadTo, bookPages));

    }
        return userBookMapper.toDto(userBookRepository.save(userBook));
    }


    public UserBooks.Status markRented(Long userID, String isbn) {
        User user = BooksJavaFunctions.findUser(userRepository, userID);
        Book book = BooksJavaFunctions.findBook(bookRepository, isbn);

        UserBookId id = new UserBookId(userID, isbn);

        UserBooks userBook = userBookRepository.findById(id).orElse(null);
        UserBooks.Status previousStatus = null;

        if (userBook == null) {
            userBook = UserBooks.builder()
                    .user(user)
                    .book(book)
                    .readingStatus(UserBooks.Status.Rented)
                    .build();
        } else {
            previousStatus = userBook.getIsDeleted() ? null : userBook.getReadingStatus();
            userBook.setIsDeleted(false);
            userBook.setReadingStatus(UserBooks.Status.Rented);
        }

        userBookRepository.save(userBook);
        return previousStatus;
    }

    public void restoreStatus(Long userID, String isbn,
                              UserBooks.Status previousStatus) {
        UserBookId id = new UserBookId(userID, isbn);
        UserBooks userBook = userBookRepository.findById(id).orElse(null);

        if (previousStatus == null) {
            if (userBook != null) {
                userBook.setIsDeleted(true);
                userBookRepository.save(userBook);
            }
            return;
        }

        if (userBook == null) {
            User user = BooksJavaFunctions.findUser(userRepository, userID);
            Book book = BooksJavaFunctions.findBook(bookRepository, isbn);

            userBook = UserBooks.builder()
                    .user(user)
                    .book(book)
                    .build();
        }

        userBook.setReadingStatus(previousStatus);
        userBookRepository.save(userBook);
    }

    public void delete(Long userID, String isbn) {
        UserBookId userBookID = new UserBookId(userID, isbn);
        UserBooks userBook = userBookRepository.findById(userBookID)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionMessages.USER_BOOK_RELATIONSHIP_NOT_FOUND));

        if(userBook.getReadingStatus() == UserBooks.Status.Rented) {
            throw new ConflictException(ExceptionMessages.CANNOT_DELETE_RENTED_BOOK);
        }
        userBook.setIsDeleted(true);
        userBookRepository.save(userBook);
    }
}