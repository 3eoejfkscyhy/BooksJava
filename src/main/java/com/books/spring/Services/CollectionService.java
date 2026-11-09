package com.books.spring.Services;

import com.books.spring.DTOs.CollectionDTO;
import com.books.spring.Mappers.CollectionMapper;
import com.books.spring.Models.Author;
import com.books.spring.Models.Book;
import com.books.spring.Models.Collection;
import com.books.spring.Repositories.BookRepository;
import com.books.spring.Repositories.CollectionRepository;
import com.books.spring.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<CollectionDTO> getAll() {
        return collectionRepository.findAll().stream()
                .filter(collection -> !collection.isDeleted())
                .map(collectionMapper::toDto)
                .toList();
    }

    public CollectionDTO getById(Long collectionID) { return collectionMapper.toDto(findCollection(collectionID)); }

    public CollectionDTO create(CollectionDTO dto) {
        Collection collection = collectionMapper.toEntity(dto);
        collection.setCollectionID(null);
        collection.setUser(
                userRepository.findById(dto.getUserID())
                        .filter(user -> !user.isDeleted())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User " + dto.getUserID() + " could not be found"
                        ))
        );

        Set<Book> books = dto.getIsbns().stream()
                .map(isbn -> bookRepository.findById(isbn)
                        .filter(book -> !book.isDeleted())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book " + isbn + "could not be found")))
                .collect(Collectors.toSet());
        collection.setBooks(books);
        return collectionMapper.toDto(collectionRepository.save(collection));
    }

    public CollectionDTO update(Long collectionID, CollectionDTO dto) {
        Collection collection = findCollection(collectionID);

        collection.setCollectionName(dto.getCollectionName());
        collection.setUser(
                userRepository.findById(dto.getUserID())
                        .filter(user -> !user.isDeleted())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User " + dto.getUserID() + " could not be found"
                        ))
        );


        Set<Book> newBooks = dto.getIsbns().stream()
                .map(isbn -> bookRepository.findById(isbn)
                        .filter(book -> !book.isDeleted())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Book " + isbn + " could not be found"
                        )))
                .collect(Collectors.toSet());
        collection.getBooks().forEach(book -> book.getCollections().remove(collection));
        collection.setBooks(newBooks);
        newBooks.forEach(book -> book.getCollections().add(collection));
        return collectionMapper.toDto(collectionRepository.save(collection));
    }

    public void delete(Long collectionID) {
        Collection collection = findCollection(collectionID);
        collection.setDeleted(true);
        collectionRepository.save(collection);
    }

    @Deprecated
    public void hardDelete(Long collectionID) {
        collectionRepository.delete(findCollection(collectionID));
    }

    private Collection findCollection(Long collectionID) {
        return collectionRepository.findById(collectionID)
                .filter(collection -> !collection.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection " + collectionID + " was not found"));
    }

    public Set<Collection> findCollections(Set<Long> collectionIDs) {
        return collectionIDs.stream()
                .map(this::findCollection)
                .collect(Collectors.toSet());
    }
}