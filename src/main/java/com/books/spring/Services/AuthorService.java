package com.books.spring.Services;

import com.books.spring.DTOs.AuthorDTO;
import com.books.spring.Mappers.AuthorMapper;
import com.books.spring.Models.Author;
import com.books.spring.Repositories.AuthorRepository;
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
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookService bookService;
    private final AuthorMapper authorMapper;

    public List<AuthorDTO> findAll(){
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

        return authorMapper.toDto(authorRepository.save(author));
    }

    private Author findAuthor(Long authorID) {
        return authorRepository.findByAuthorID(authorID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author " + authorID + " could not be found"));
    }
    public Set<Author> findAuthors(Set<Long> authorIDs) {
        return authorIDs.stream()
                .map(this::findAuthor)
                .collect(Collectors.toSet());
    }
}