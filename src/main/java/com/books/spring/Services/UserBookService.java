package com.books.spring.Services;

import com.books.spring.DTOs.UserBookDTO;
import com.books.spring.Mappers.UserBookMapper;
import com.books.spring.Misc.UserBookId;
import com.books.spring.Models.*;
import com.books.spring.Repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
                .filter(userBook -> !userBook.getBook().getIsDeleted())
                .map(userBookMapper::toDto)
                .toList();
    }

    public UserBookDTO purchase(long userID, String isbn) {
        User user = findUser(userID);
        Book book = findBook(isbn);

        if(book.getBookType() != Book.BookType.EBook) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only Ebooks can be purchased through the application");
        }

        UserBookId id = new UserBookId(userID, isbn);
        UserBooks userBooks = userBookRepository.findById(id).orElse(null);
        if(userBooks != null && userBooks.getReadingStatus() != UserBooks.Status.Wishlisted) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This user already owns this book");
        }
        if(user.getBalance().compareTo(book.getPrice()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
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
            userBooks.setReadingStatus(UserBooks.Status.Owned);
        }

        return userBookMapper.toDto(userBookRepository.save(userBooks));
    }

    public UserBookDTO create(UserBookDTO dto) {
        User user = findUser(dto.userID());
        Book book = findBook(dto.isbn());

        UserBookId id = new UserBookId(user.getUserID(), book.getIsbn());
        if(userBookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This user already has this book in their reading list");
        }
         if(dto.lastReadTo()>book.getBookLength()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The given page that was last read was bigger than the ammount of pages there are");
        }
        UserBooks userBook = UserBooks.builder()
                .user(user)
                .book(book)
                .readingStatus(dto.readingStatus())
                .lastReadTo(dto.lastReadTo())
                .readingPercentage(percentageRead(dto.lastReadTo(),book.getBookLength()))
                .build();

        return userBookMapper.toDto(userBookRepository.save(userBook));
    }

    public UserBookDTO update(Long userID, String isbn, Long lastReadTo, UserBooks.Status status) {
        UserBookId userBookID = new UserBookId(userID, isbn);
        Long bookPages = findBook(isbn).getBookLength();
        UserBooks userBook = userBookRepository.findById(userBookID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User-book relationship not found"));
        userBook.setReadingStatus(status);
                 if(lastReadTo>bookPages) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The given page that was last read was bigger than the ammount of pages there are");
        }
        userBook.setLastReadTo(lastReadTo);
        userBook.setReadingPercentage(percentageRead(lastReadTo,bookPages));
        return userBookMapper.toDto(userBookRepository.save(userBook));
    }

    public void delete(Long userID, String isbn) {
        UserBookId id = new UserBookId(userID, isbn);
        userBookRepository.deleteById(id);
    }

    private User findUser(Long userID) {
        return userRepository.findByUserID(userID)
                .filter(user -> !user.getIsDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User could not be found"));
    }

    private Book findBook(String isbn) {
        return bookRepository.findById(isbn)
                .filter(book -> !book.getIsDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book could not be found"));
    }

     public BigDecimal percentageRead(long lastReadTo,long bookLenght){
      float fraction =  (float) lastReadTo/bookLenght;
      float uncleanPer = fraction*100;
      return new BigDecimal((long)uncleanPer).setScale(2,RoundingMode.HALF_UP);
  } 
}