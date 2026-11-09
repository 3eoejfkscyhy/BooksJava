package com.books.spring.Services;

import com.books.spring.DTOs.UserBookDTO;
import com.books.spring.Mappers.UserBookMapper;
import com.books.spring.Models.Book;
import com.books.spring.Models.User;
import com.books.spring.Models.UserBookId;
import com.books.spring.Models.UserBooks;
import com.books.spring.Repositories.BookRepository;
import com.books.spring.Repositories.UserBookRepository;
import com.books.spring.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .filter(userBook -> !userBook.getBook().isDeleted())
                .map(userBookMapper::toDto)
                .toList();
    }

    public UserBookDTO create(UserBookDTO dto) {
        User user = findUser(dto.getUserID());
        Book book = findBook(dto.getIsbn());

        UserBookId id = new UserBookId(user.getUserID(), book.getIsbn());
        if(userBookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This user already has this book in their reading list");
        }

        UserBooks userBook = UserBooks.builder()
                .user(user)
                .book(book)
                .readingStatus(dto.getReadingStatus())
                .build();

        return userBookMapper.toDto(userBookRepository.save(userBook));
    }

    public UserBookDTO update(Long userID, String isbn, UserBooks.Status status) {
        UserBookId userBookID = new UserBookId(userID, isbn);

        UserBooks userBook = userBookRepository.findById(userBookID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User-book relationship not found"));
        userBook.setReadingStatus(status);

        return userBookMapper.toDto(userBookRepository.save(userBook));
    }

    public void delete(Long userID, String isbn) {
        UserBookId id = new UserBookId(userID, isbn);
        userBookRepository.deleteById(id);
    }

    private User findUser(Long userID) {
        return userRepository.findByUserID(userID)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User could not be found"));
    }

    private Book findBook(String isbn) {
        return bookRepository.findById(isbn)
                .filter(book -> !book.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book could not be found"));
    }
}