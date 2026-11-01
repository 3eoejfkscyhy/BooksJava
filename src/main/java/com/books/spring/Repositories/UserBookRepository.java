package com.books.spring.Repositories;

import com.books.spring.Models.UserBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface UserBookRepository extends JpaRepository<UserBooks,Long> {

}