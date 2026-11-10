package com.books.spring.Services;

import com.books.spring.DTOs.BookDTO;
import com.books.spring.DTOs.FreePeriodDTO;
import com.books.spring.Mappers.BookMapper;
import com.books.spring.Models.Author;
import com.books.spring.Models.Book;
import com.books.spring.Models.Collection;
import com.books.spring.Models.Genre;
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
        validateBookTypeAndRentalPrice(dto);

        Book book = bookMapper.toEntity(dto);

        Set<Author> authors = authorService.findAuthors(dto.getAuthorIDs());
        book.setBookAuthors(authors);
        authors.forEach(author -> author.getBooks().add(book));

        Set<Genre> genres = genreService.findGenres(dto.getGenreIDs());
        book.setBookGenres(genres);
        genres.forEach((genre -> genre.getBooks().add(book)));

        return bookMapper.toDto(bookRepository.save(book));
    }

    public BookDTO update(String isbn, BookDTO dto) {
        validateBookTypeAndRentalPrice(dto);

        Book book = findBook(isbn);

        book.setTitle(dto.getTitle());
        book.setLanguage(dto.getLanguage());
        book.setBookLength(dto.getBookLength());
        book.setPrice(dto.getPrice());
        book.setBookType(dto.getBookType());
        book.setRentalPrice(dto.getRentalPrice());

        Set<Author> newAuthors = authorService.findAuthors(dto.getAuthorIDs());
        book.getBookAuthors().forEach(author -> author.getBooks().remove(book));
        book.setBookAuthors(newAuthors);
        newAuthors.forEach(author -> author.getBooks().add(book));

        Set<Genre> newGenres = genreService.findGenres(dto.getGenreIDs());
        book.getBookGenres().forEach(genre -> genre.getBooks().remove(book));
        book.setBookGenres(newGenres);
        newGenres.forEach(genre -> genre.getBooks().add(book));

        Set<Collection> newCollections = collectionService.findCollections(dto.getCollectionIDs());
        book.getCollections().forEach(collection -> collection.getBooks().remove(book));
        book.setCollections(newCollections);
        newCollections.forEach(collection -> collection.getBooks().add(book));

        return bookMapper.toDto(bookRepository.save(book));
    }

    public BookDTO setFreePeriod(String isbn, FreePeriodDTO dto) {
        Book book = findBook(isbn);

        if(!dto.getFreeFrom().isBefore(dto.getFreeUntil())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The free period must begin before it ends");
        }

        book.setFreeFrom(dto.getFreeFrom());
        book.setFreeUntil(dto.getFreeUntil());
        return bookMapper.toDto(bookRepository.save(book));
    }

    public BookDTO removeFreePeriod(String isbn) {
        Book book = findBook(isbn);
        book.setFreeFrom(null);
        book.setFreeUntil(null);
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

    private void validateBookTypeAndRentalPrice(BookDTO dto) {
        if(dto.getBookType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book type is required");
        }
        if(dto.getBookType() == Book.BookType.EBook && dto.getRentalPrice() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-books must have a rental price");
        }
        if(dto.getBookType() == Book.BookType.PhysicalBook && dto.getRentalPrice() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Physical books cannot be rentable");
        }
    }

    private Book findBook(String isbn) {
        return bookRepository.findById(isbn)
                .filter(book -> !book.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book " + isbn + " was not found"));
    }
}