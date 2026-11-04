package com.books.spring.Services;

import com.books.spring.DTOs.GenreDTO;
import com.books.spring.Mappers.GenreMapper;
import com.books.spring.Models.Author;
import com.books.spring.Models.Book;
import com.books.spring.Models.Genre;
import com.books.spring.Repositories.BookRepository;
import com.books.spring.Repositories.GenreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public GenreDTO getById(Long genreID) { return genreMapper.toDto(findGenre(genreID)); }

    public GenreDTO create(GenreDTO dto) {
        if(genreRepository.existsByGenreName(dto.getGenreName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Genre already exists.");
        }

        Genre genre = genreMapper.toEntity(dto);
        genre.setGenreID(null);
        return genreMapper.toDto(genreRepository.save(genre));
    }

    public GenreDTO update(Long genreID, GenreDTO dto) {
        Genre genre = findGenre(genreID);

        if(!dto.getGenreName().equals(genre.getGenreName()) && genreRepository.existsByGenreName(dto.getGenreName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Genre already exists.");
        }

        genre.setGenreName(dto.getGenreName());
        genre.setBooks(
                dto.getIsbns().stream()
                        .map(isbn -> bookRepository.findByisbn(isbn)
                                .orElseThrow(() -> new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Book " + isbn + " could not be found"
                                )))
                        .collect(Collectors.toSet())
        );
        Set<Book> books = dto.getIsbns().stream()
                .map(isbn -> bookRepository.findByisbn(isbn)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Book " + isbn + " could not be found"
                        )))
                .collect(Collectors.toSet());
        genre.getBooks().forEach(book -> book.getBookGenres().remove(genre));
        genre.setBooks(books);
        books.forEach(book -> book.getBookGenres().add(genre));
        return genreMapper.toDto(genreRepository.save(genre));
    }

    private Genre findGenre(Long genreID) {
        return genreRepository.findBygenreID(genreID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre " + genreID + " could not be found"));
    }
    public Set<Genre> findGenres(Set<Long> genreIDs) {
        return genreIDs.stream()
                .map(this::findGenre)
                .collect(Collectors.toSet());
    }
}

