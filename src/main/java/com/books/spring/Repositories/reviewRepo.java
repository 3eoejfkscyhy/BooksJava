package com.books.spring.Repositories;

//import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.books.spring.Models.Review;


@Repository 
public interface ReviewRepo extends JpaRepository<Review,Long> {


 Optional<Review> findByHeadline(String headline);


}
