package com.books.spring.Services;

import com.books.spring.DTOs.BookDTO;
import com.books.spring.DTOs.FreePeriodDTO;
import com.books.spring.Mappers.BookMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Misc.BooksJavaFunctions;
import com.books.spring.Models.*;
import com.books.spring.Repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.books.spring.Exception.*;

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
                .map(bookMapper::toDto)
                .toList();
    }

    public BookDTO getByIsbn(String isbn) {

          return bookMapper.toDto(BooksJavaFunctions.findBook(bookRepository,isbn));
    }

    public BookDTO create(BookDTO dto) {
        validateBookTypeAndRentalPrice(dto);

        Book book = bookMapper.toEntity(dto);

        Set<Author> authors = authorService.findAuthors(dto.authorIDs());
        book.setBookAuthors(authors);
        authors.forEach(author -> author.getBooks().add(book));

        Set<Review> reviews = reviewService.findReviews(dto.reviewIDs());
        book.setReviews(reviews);
        reviews.forEach(review -> review.setBook(book));

        Set<Genre> genres = genreService.findGenres(dto.genreIDs());
        book.setBookGenres(genres);
        genres.forEach((genre -> genre.getBooks().add(book)));

        Set<Collection> collections = collectionService.findCollections(dto.collectionIDs());
        book.setCollections(collections);
        collections.forEach(collection -> collection.getBooks().add(book));


        return bookMapper.toDto(bookRepository.save(book));
    }

    public BookDTO update(String isbn, BookDTO dto) {
        validateBookTypeAndRentalPrice(dto);

        Book book = BooksJavaFunctions.findBook(bookRepository,isbn);

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
        Book book = BooksJavaFunctions.findBook(bookRepository,isbn);

        if(!dto.freeFrom().isBefore(dto.freeUntil())) {
            throw new BadRequestException(ExceptionMessages.FREE_PERIOD_MUST_BEGIN_BEFORE_END);
        }

        book.setFreeFrom(dto.freeFrom());
        book.setFreeUntil(dto.freeUntil());
        return bookMapper.toDto(bookRepository.save(book));
    }

    public BookDTO removeFreePeriod(String isbn) {
        Book book = BooksJavaFunctions.findBook(bookRepository,isbn);
        book.setFreeFrom(null);
        book.setFreeUntil(null);
        return bookMapper.toDto(bookRepository.save(book));
    }

    public void delete(String isbn) {
        bookRepository.delete(BooksJavaFunctions.findBook(bookRepository,isbn));
    }

    private void validateBookTypeAndRentalPrice(BookDTO dto) {
        if(dto.bookType() == null) {
            throw new BadRequestException(ExceptionMessages.BOOK_TYPE_REQUIRED);
        }
        if(dto.bookType() == Book.BookType.EBook && dto.rentalPrice() == null) {
            throw new BadRequestException(ExceptionMessages.BOOK_HAS_RENTAL_PRICE);
        }
        if(dto.bookType() == Book.BookType.PhysicalBook && dto.rentalPrice() != null) {
            throw new BadRequestException(ExceptionMessages.PHYSICAL_BOOKS_CANT_BE_RENTED);
        }
    }
}