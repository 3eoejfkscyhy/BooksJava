package com.books.spring.Services;

import com.books.spring.DTOs.BookDTO;
import com.books.spring.DTOs.FreePeriodDTO;
import com.books.spring.Mappers.BookMapper;
import com.books.spring.Models.Author;
import com.books.spring.Models.Book;
import com.books.spring.Models.Collection;
import com.books.spring.Models.Genre;
import com.books.spring.Models.Review;
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
     private final ReviewService reviewService;
    private final CollectionService collectionService;

    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream()
                .filter(book -> !book.getIsDeleted())
                .map(bookMapper::toDto)
                .toList();
    }

    public BookDTO getByIsbn(String isbn) {
        return bookMapper.toDto(findBook(isbn));
    }

    public BookDTO create(BookDTO dto) {
        validateBookTypeAndRentalPrice(dto);

        Book book = bookMapper.toEntity(dto);

        Set<Author> authors = authorService.findAuthors(dto.authorIDs());
        book.setBookAuthors(authors);
        authors.forEach(author -> author.getBooks().add(book));

        Set<Review> reviews = reviewService.findReviews(dto.reviewIDs());
        book.setReviews(reviews);

        Set<Genre> genres = genreService.findGenres(dto.genreIDs());
        book.setBookGenres(genres);
        genres.forEach((genre -> genre.getBooks().add(book)));

        Set<Collection> collections = collectionService.findCollections(dto.collectionIDs());
        book.setCollections(collections);
       

        return bookMapper.toDto(bookRepository.save(book));
    }

    public BookDTO update(String isbn, BookDTO dto) {
        validateBookTypeAndRentalPrice(dto);

        Book book = findBook(isbn);

        book.setTitle(dto.title());
        book.setLanguage(dto.language());
        book.setBookLength(dto.bookLength());
        book.setPrice(dto.price());
        book.setBookType(dto.bookType());
        book.setRentalPrice(dto.rentalPrice());

        Set<Author> newAuthors = authorService.findAuthors(dto.authorIDs());
        book.getBookAuthors().forEach(author -> author.getBooks().remove(book));
        book.setBookAuthors(newAuthors);
        newAuthors.forEach(author -> author.getBooks().add(book));

        Set<Genre> newGenres = genreService.findGenres(dto.genreIDs());
        book.getBookGenres().forEach(genre -> genre.getBooks().remove(book));
        book.setBookGenres(newGenres);
        newGenres.forEach(genre -> genre.getBooks().add(book));

        Set<Collection> newCollections = collectionService.findCollections(dto.collectionIDs());
        book.getCollections().forEach(collection -> collection.getBooks().remove(book));
        book.setCollections(newCollections);
        newCollections.forEach(collection -> collection.getBooks().add(book));

        return bookMapper.toDto(bookRepository.save(book));
    }

    public BookDTO setFreePeriod(String isbn, FreePeriodDTO dto) {
        Book book = findBook(isbn);

        if(!dto.freeFrom().isBefore(dto.freeUntil())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The free period must begin before it ends");
        }

        book.setFreeFrom(dto.freeFrom());
        book.setFreeUntil(dto.freeUntil());
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
        book.setIsDeleted(true);
        bookRepository.save(book);
    }

    @Deprecated
    public void hardDelete(String isbn) {
        bookRepository.delete(findBook(isbn));
    }

    private void validateBookTypeAndRentalPrice(BookDTO dto) {
        if(dto.bookType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book type is required");
        }
        if(dto.bookType() == Book.BookType.EBook && dto.rentalPrice() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-books must have a rental price");
        }
        if(dto.bookType() == Book.BookType.PhysicalBook && dto.rentalPrice() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Physical books cannot be rentable");
        }
    }

    private Book findBook(String isbn) {
        return bookRepository.findById(isbn)
                .filter(book -> !book.getIsDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book " + isbn + " was not found"));
    }
}