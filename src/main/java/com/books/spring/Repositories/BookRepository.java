package com.books.spring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.books.spring.Models.Book;


@Repository 
public interface BookRepository extends JpaRepository<Book, String> {

  Optional<Book> findBytitle(String title);

  boolean existsByTitle(String title);

}
