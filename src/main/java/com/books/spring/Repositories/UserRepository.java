package com.books.spring.Repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.books.spring.Models.User;

@Repository 
public interface UserRepository extends JpaRepository<User,Long> {

 Optional<User> findByUserID(Long userID);
}
