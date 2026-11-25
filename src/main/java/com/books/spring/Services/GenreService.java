package com.books.spring.Services;

import com.books.spring.DTOs.GenreDTO;
import com.books.spring.Mappers.GenreMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Models.Book;
import com.books.spring.Models.Genre;
import com.books.spring.Repositories.BookRepository;
import com.books.spring.Repositories.GenreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.books.spring.Exception.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final BookRepository bookRepository;

    public List<GenreDTO> getAll() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toDto)
                .toList();
    }

    public GenreDTO getByID(Long genreID) { return genreMapper.toDto(findGenre(genreID)); }

    public GenreDTO create(GenreDTO dto) {
        String normalisedName = normaliseGenreName(dto.genreName());
        if(genreRepository.existsByGenreNameIgnoreCase(normalisedName)) {
            throw new ConflictException((ExceptionMessages.GENRE_EXIST));
        }

        Genre genre = genreMapper.toEntity(dto);
        genre.setGenreID(null);
        genre.setGenreName(normalisedName);
        if(dto.isbns()!=null)
        {
        Set<Book> books = dto.isbns().stream()
                .map(isbn -> bookRepository.findById(isbn)
                        .orElseThrow(() -> new NotFoundException(
                                ExceptionMessages.GIVEN_BOOK_NOT_FOUND.formatted(isbn)
                        )))
                .collect(Collectors.toSet());
        genre.getBooks().forEach(book -> book.getBookGenres().remove(genre));
        genre.setBooks(books);
        books.forEach(book -> book.getBookGenres().add(genre));
        }
        return genreMapper.toDto(genreRepository.save(genre));
    }

    public GenreDTO update(Long genreID, GenreDTO dto) {
        Genre genre = findGenre(genreID);

        if(!dto.genreName().equals(genre.getGenreName()) && genreRepository.existsByGenreName(dto.genreName())) {
            throw new ConflictException(ExceptionMessages.GENRE_EXIST);
        }

                if(dto.isbns()!=null)
        {
        genre.setGenreName(dto.genreName());
        Set<Book> books = dto.isbns().stream()
                .map(isbn -> bookRepository.findById(isbn)
                        .orElseThrow(() -> new NotFoundException(
                                ExceptionMessages.GIVEN_BOOK_NOT_FOUND.formatted(isbn)
                        )))
                .collect(Collectors.toSet());
        genre.getBooks().forEach(book -> book.getBookGenres().remove(genre));
        genre.setBooks(books);
        books.forEach(book -> book.getBookGenres().add(genre));
                    }
        return genreMapper.toDto(genreRepository.save(genre));
    }

    public void delete(Long genreID) {
        genreRepository.delete(findGenre(genreID));
    }

    private Genre findGenre(Long genreID) {
        return genreRepository.findByGenreID(genreID)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.GIVEN_GENRE_NOT_FOUND.formatted(genreID)));
    }
    public Set<Genre> findGenres(Set<Long> genreIDs) {
        return genreIDs.stream()
                .map(this::findGenre)
                .collect(Collectors.toSet());
    }

    private String normaliseGenreName(String name) {
        return name.trim().toLowerCase();
    }
}

