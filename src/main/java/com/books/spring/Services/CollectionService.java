package com.books.spring.Services;

import com.books.spring.DTOs.CollectionDTO;
import com.books.spring.Mappers.CollectionMapper;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;
import com.books.spring.Models.Book;
import com.books.spring.Models.Collection;
import com.books.spring.Repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.books.spring.Exception.*;

import java.util.*;
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
                .map(collectionMapper::toDto)
                .toList();
    }

    public CollectionDTO getByID(Long collectionID) { return collectionMapper.toDto(findCollection(collectionID)); }

    public CollectionDTO create(CollectionDTO dto) {
        Collection collection = collectionMapper.toEntity(dto);
        collection.setCollectionID(null);
          if(dto.userID()!=null)
        {
        collection.setUser(
                userRepository.findById(dto.userID())
                        .orElseThrow(() -> new NotFoundException(
                                 ExceptionMessages.GIVEN_USER_NOT_FOUND.formatted(dto.userID())
                        ))
        );
       }

         if(dto.isbns()!=null)
        {
        Set<Book> books = dto.isbns().stream()
                .map(isbn -> bookRepository.findById(isbn)
                        .orElseThrow(() -> new NotFoundException( ExceptionMessages.GIVEN_BOOK_NOT_FOUND.formatted(isbn))))
                .collect(Collectors.toSet());
        collection.setBooks(books);
        }
        return collectionMapper.toDto(collectionRepository.save(collection));
    }

    public CollectionDTO update(Long collectionID, CollectionDTO dto) {
        Collection collection = findCollection(collectionID);
      if(dto.userID()!=null)
        {
        collection.setCollectionName(dto.collectionName());
        collection.setUser(
                userRepository.findById(dto.userID())
                        .orElseThrow(() -> new NotFoundException(
                                 ExceptionMessages.GIVEN_USER_NOT_FOUND.formatted(dto.userID())
                        ))
        );
        }

        if(dto.isbns()!=null)
        {
        Set<Book> newBooks = dto.isbns().stream()
                .map(isbn -> bookRepository.findById(isbn)
                        .orElseThrow(() -> new NotFoundException(
                                ExceptionMessages.GIVEN_BOOK_NOT_FOUND.formatted(isbn)
                        )))
                .collect(Collectors.toSet());
        collection.getBooks().forEach(book -> book.getCollections().remove(collection));
        collection.setBooks(newBooks);
        newBooks.forEach(book -> book.getCollections().add(collection));
                }
        return collectionMapper.toDto(collectionRepository.save(collection));
    }

    public void delete(Long collectionID) {
        collectionRepository.delete(findCollection(collectionID));
    }

    private Collection findCollection(Long collectionID) {
        return collectionRepository.findByCollectionID(collectionID)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.GIVEN_COLLECTION_NOT_FOUND.formatted(collectionID)));
    }

    public Set<Collection> findCollections(Set<Long> collectionIDs) {
        if(collectionIDs == null) {
            return new HashSet<>();
        }
        return collectionIDs.stream()
                .map(this::findCollection)
                .collect(Collectors.toSet());
    }
}