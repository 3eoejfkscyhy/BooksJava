package com.books.spring.Repositories;

import com.books.spring.Models.Author;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface AuthorRepository extends JpaRepository<Author,Long> {

   Optional<Author> findByfirstName(String firstName);
   Optional<Author> findBylastName(String lastName);
   Optional<Author> findByfirstNameAndLastName(String firstName,String lastName);
}