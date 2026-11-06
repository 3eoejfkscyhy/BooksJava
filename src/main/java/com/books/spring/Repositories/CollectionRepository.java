package com.books.spring.Repositories;

import com.books.spring.Models.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface CollectionRepository extends JpaRepository<Collection,Long> {
    
Optional<Collection> findBycollectionName(String collectionName);
Optional<Collection> findBycollectionID(Long collectionID);
}