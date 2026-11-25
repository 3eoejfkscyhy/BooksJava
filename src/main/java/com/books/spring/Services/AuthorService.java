package com.books.spring.Services;

import com.books.spring.DTOs.AuthorDTO;
import com.books.spring.Exception.NotFoundException;
import com.books.spring.Mappers.AuthorMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Models.Author;
import com.books.spring.Models.Book;
import com.books.spring.Repositories.AuthorRepository;
import com.books.spring.Repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;

    public List<AuthorDTO> getAll(){
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .toList();
    }

    public AuthorDTO getByID(Long authorID) {
        return authorMapper.toDto(findAuthor(authorID));
    }

    public AuthorDTO create(AuthorDTO dto) {
        Author author = authorMapper.toEntity(dto);
        author.setAuthorID(null);
        if(dto.isbns()!=null)
        {
       Set<Book> newBooks = dto.isbns().stream()
                .map(isbn -> bookRepository.findById(isbn)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessages.GIVEN_BOOK_NOT_FOUND.formatted(isbn))))
                .collect(Collectors.toSet());
        author.getBooks().forEach(book -> book.getBookAuthors().remove(author));
        author.setBooks(newBooks);
        newBooks.forEach(book -> book.getBookAuthors().add(author));
        }
        return authorMapper.toDto(authorRepository.save(author));
    }

    public AuthorDTO update(Long authorID, AuthorDTO dto) {
        Author author = findAuthor(authorID);

        author.setFirstName(dto.firstName());
        author.setLastName(dto.lastName());
        author.setDOB(dto.DOB());
        author.setBooksWritten(dto.booksWritten());
      if(dto.isbns()!=null)
        {
        Set<Book> newBooks = dto.isbns().stream()
                .map(isbn -> bookRepository.findById(isbn)
                    .orElseThrow(() -> new NotFoundException( ExceptionMessages.GIVEN_BOOK_NOT_FOUND.formatted(isbn))))
                .collect(Collectors.toSet());
        author.getBooks().forEach(book -> book.getBookAuthors().remove(author));
        author.setBooks(newBooks);
        newBooks.forEach(book -> book.getBookAuthors().add(author));
        }

        return authorMapper.toDto(authorRepository.save(author));
    }

    public void delete(Long authorID) {
        authorRepository.delete(findAuthor(authorID));
    }

    private Author findAuthor(Long authorID) {
        return authorRepository.findByAuthorID(authorID)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.GIVEN_AUTHOR_NOT_FOUND.formatted(authorID)));
    }
    public Set<Author> findAuthors(Set<Long> authorIDs) {
        if(authorIDs == null) {
            return new HashSet<>();
        }
        return authorIDs.stream()
                .map(this::findAuthor)
                .collect(Collectors.toSet());
    }
}