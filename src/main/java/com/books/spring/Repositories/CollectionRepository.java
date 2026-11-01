package com.books.spring.Repositories;

import com.books.spring.Models.Collection;
import com.books.spring.Models.Genre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface CollectionRepository extends JpaRepository<Collection,Long> {
    
Optional<Genre> findBycollectionName(String collectionName);
}