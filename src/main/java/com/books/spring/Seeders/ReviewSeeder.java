package com.books.spring.Seeders;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import com.books.spring.Models.Review;
import com.books.spring.Models.User;

import com.books.spring.Repositories.ReviewRepository;
import com.books.spring.Repositories.UserRepository;
// import  com.books.spring.Repositories.BookRepository;
import  lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;



@Slf4j
@Component 
@RequiredArgsConstructor
@Order(2)
public class ReviewSeeder implements CommandLineRunner {
     private final ReviewRepository reviewRepo;
     private final UserRepository userRepo;
   //   private final BookRepository bookRepo;
     @Override
     public void run(String... args) throws Exception  {
        if(reviewRepo.count()>0)
        {
            log.info("oefnn");
            return;  
        }

     Review Jones = Review.builder()
     .headline("oyoyoyo")
     .body("HUhd")
     .stars(new BigDecimal(2.5))
     .writtenBy(findUser((long) 2))
     //.book(findBook(("9781784758240")))
     .build();

  Review Skips = Review.builder()
     .headline("oyoyodmfpmeyo")
     .body("HU04er22k0hd")
     .stars(new BigDecimal(2.0))
    .writtenBy(findUser((long) 2))
    //.book(findBook(("9781784758240")))
     .build();

         reviewRepo.saveAll(List.of(Jones,Skips));
     
     log.info("Done");
   }
   private User findUser(Long user_id)
   {
      return userRepo.findByUserID(user_id).orElseThrow(()-> new IllegalStateException("Cannot seed Reviews: user don't exits"));
   }
   // private Book findBook(String isbn)
   // {
   //    return bookRepo.findBookByISBN(new Long(isbn));
   // }
}

