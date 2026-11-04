package com.books.spring.Services;

import com.books.spring.DTOs.CollectionDTO;
import com.books.spring.Mappers.CollectionMapper;
import com.books.spring.Models.Collection;
import com.books.spring.Repositories.BookRepository;
import com.books.spring.Repositories.CollectionRepository;
import com.books.spring.Repositories.UserRepository;
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

    public CollectionDTO getById(Long collectionID) { return collectionMapper.toDto(findCollection(collectionID)); }

    public CollectionDTO create(CollectionDTO dto) {
        Collection collection = collectionMapper.toEntity(dto);
        collection.setCollectionID(null);
        collection.setUser(
                userRepository.findById(dto.getUserID())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User " + dto.getUserID() + " could not be found"
                        ))
        );
        return collectionMapper.toDto(collectionRepository.save(collection));
    }

    public CollectionDTO update(Long collectionID, CollectionDTO dto) {
        Collection collection = findCollection(collectionID);

        collection.setCollectionName(dto.getCollectionName());
        collection.setUser(
                userRepository.findById(dto.getUserID())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User " + dto.getUserID() + " could not be found"
                        ))
        );
        collection.setBooks(
                dto.getIsbns().stream()
                        .map(isbn -> bookRepository.findByisbn(isbn)
                                .orElseThrow(() -> new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Book " + isbn + " could not be found"
                                )))
                        .collect(Collectors.toSet())
        );
        return collectionMapper.toDto(collectionRepository.save(collection));
    }

    private Collection findCollection(Long collectionID) {
        return collectionRepository.findById(collectionID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection " + collectionID + " was not found"));
    }

    public Set<Collection> findCollections(Set<Long> collectionIDs) {
        return collectionIDs.stream()
                .map(this::findCollection)
                .collect(Collectors.toSet());
    }
}