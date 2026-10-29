package com.books.spring.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.books.spring.Models.User;


@Repository 
public interface UserRepo extends JpaRepository<User,Long> {
 List<User> findByUsername(String username);

 List<User> findByUserID(Long userID);

 Optional<User> findByEmail(String email);

 boolean existsByEmail(String email);
}
