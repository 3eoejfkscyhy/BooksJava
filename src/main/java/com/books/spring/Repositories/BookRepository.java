package com.books.spring.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.books.spring.Models.Book;


@Repository 
public interface BookRepository extends JpaRepository<Book,Long> {

  Optional<Book> findByisbn(Long isbn);
  Optional<Book> findBytitle(String bookName);

}
