package com.books.spring.Services;

import com.books.spring.DTOs.BookDTO;
import com.books.spring.Mappers.BookMapper;
import com.books.spring.Models.Book;
import com.books.spring.Models.Collection;
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
    private final GenreService genreService;
    private final CollectionService collectionService;

    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream()
                .filter(book -> !book.isDeleted())
                .map(bookMapper::toDto)
                .toList();
    }

    public BookDTO getByIsbn(String isbn) {
        return bookMapper.toDto(findBook(isbn));
    }

    public BookDTO create(BookDTO dto) {
        Book book = bookMapper.toEntity(dto);

        book.setBookAuthors(authorService.findAuthors(dto.getAuthorIDs()));
        book.setBookGenres(genreService.findGenres(dto.getGenreIDs()));

        return bookMapper.toDto(bookRepository.save(book));
    }

    public BookDTO update(String isbn, BookDTO dto) {
        Book book = findBook(isbn);

        book.setTitle(dto.getTitle());
        book.setBookAuthors(authorService.findAuthors(dto.getAuthorIDs()));
        book.setLanguage(dto.getLanguage());
        book.setBookLength(dto.getBookLength());
        book.setPrice(dto.getPrice());
        book.setBookGenres(genreService.findGenres(dto.getGenreIDs()));

        Set<Collection> newCollections = collectionService.findCollections(dto.getCollectionIDs());
        book.getCollections().forEach(collection -> collection.getBooks().remove(book));
        book.setCollections(newCollections);
        newCollections.forEach(collection -> collection.getBooks().add(book));

        return bookMapper.toDto(bookRepository.save(book));
    }

    public void delete(String isbn) {
        Book book = findBook(isbn);
        book.setDeleted(true);
        bookRepository.save(book);
    }

    @Deprecated
    public void hardDelete(String isbn) {
        bookRepository.delete(findBook(isbn));
    }

    private Book findBook(String isbn) {
        return bookRepository.findById(isbn)
                .filter(book -> !book.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book " + isbn + " was not found"));
    }
}