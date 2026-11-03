package com.books.spring.Services;

import com.books.spring.DTOs.BookDTO;
import com.books.spring.Mappers.BookMapper;
import com.books.spring.Models.Author;
import com.books.spring.Services.AuthorService;
import com.books.spring.Models.Book;
import com.books.spring.Repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;

    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public BookDTO getByIsbn(Long isbn) {
        return bookMapper.toDto(findBook(isbn));
    }

    public BookDTO create(BookDTO dto) {
        Book book = bookMapper.toEntity(dto);

        book.setBookAuthors(authorService.findAuthors(dto.getAuthorIDs()));

        return bookMapper.toDto(bookRepository.save(book));

    }

    private Book findBook(Long isbn) {
        return bookRepository.findByisbn(isbn)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book" + isbn + " was not found"));
    }


}