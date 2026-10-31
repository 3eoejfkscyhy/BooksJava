package com.books.spring.Seeders;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import com.books.spring.Models.Review;
import com.books.spring.Models.User;

import com.books.spring.Repositories.ReviewRepository;
import com.books.spring.Repositories.UserRepository;

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
            log.info("Reviews already exists. Skipping seeding reviews.");
            return;  
        }

     Review Henry = Review.builder()
     .headline("This Book is awesome!!")
     .body("I read this book in 3 days and it was a very interesting dive into Henry's history. Definitely check it out!")
     .stars(new BigDecimal(4.5))
     .writtenBy(findUser(2L))
     .build();

  Review Jamie = Review.builder()
     .headline("The worst cookbook i ever read!")
     .body("So i'm a chef and wanted to expand my skill set, so i bought this book and i was very dissapointed. I cooked the fried rice and it was awful, Don't recommend!")
     .stars(new BigDecimal(0.0))
    .writtenBy(findUser(3L))
     .build();

       Review Stoichkov = Review.builder()
     .headline("Could have been better")
     .body("So i am a fan of Barcelona and it's legends. So when i heard about this book, i was pretty excited. When i read it though i was very disapointed. It was very shallow and boring.")
     .stars(new BigDecimal(2.0))
    .writtenBy(findUser(2L))
     .build();
     
       Review Steve = Review.builder()
     .headline("The book is informative.")
     .body("")
     .stars(new BigDecimal(3.5))
    .writtenBy(findUser(4L))
     .build();

         reviewRepo.saveAll(List.of(Henry,Jamie,Stoichkov,Steve));
     
     log.info("Reviews succesfully seeded!");
   }
   private User findUser(Long user_id)
   {
      return userRepo.findByUserID(user_id).orElseThrow(()-> new IllegalStateException("Cannot seed Reviews: user don't exits"));
   }
}

