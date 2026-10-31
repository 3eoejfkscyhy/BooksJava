package com.books.spring.Repositories;
import com.books.spring.Models.Author;

//import java.util.List;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository 
public interface AuthorRepository extends JpaRepository<Author,Long> {


}