package com.books.spring.Repositories;

import com.books.spring.Models.UserBookId;
import com.books.spring.Models.UserBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository 
public interface UserBookRepository extends JpaRepository<UserBooks, UserBookId> {
    List<UserBooks> findByUser_UserID(Long userID);

}