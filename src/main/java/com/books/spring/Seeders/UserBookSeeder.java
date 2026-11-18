package com.books.spring.Seeders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import  java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.books.spring.Models.User;
import com.books.spring.Models.Book;
import com.books.spring.Models.UserBooks;
import com.books.spring.Models.UserBooks.Status;
import com.books.spring.Repositories.UserRepository;
import com.books.spring.Repositories.BookRepository;
import com.books.spring.Repositories.UserBookRepository;
import  lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j 
@Component 
@RequiredArgsConstructor
@Order(7)
public class UserBookSeeder implements CommandLineRunner{
    private final BookRepository bookRepo;
    private final UserRepository userRepo;
    private final UserBookRepository userBookRepo;
    @Override 
    public void run(String... args) throws Exception
    {
        if(userBookRepo.count()>0)
        {
             log.info("User Books already exists. Skipping seeding user_books.");
            return;
        }
 
   UserBooks Link1 = UserBooks.builder()
  .user(findUser(1L))
  .book(findBook("9788178530857"))
  .readingStatus(Status.Owned)
  .lastReadTo(30L)
  .readingPercentage(percentageRead(30L,findBook("9788178530857").getBookLength()))
  .build();

   UserBooks Link2 = UserBooks.builder()
  .user(findUser(3L))
  .book(findBook("9788178530857"))
  .readingStatus(Status.Wishlisted)
  .lastReadTo(0L)
 .readingPercentage(percentageRead(0L,findBook("9788178530857").getBookLength()))
  .build();

   UserBooks Link3 = UserBooks.builder()
  .user(findUser(3L))
  .book(findBook("9789545280320")) 
  .readingStatus(Status.Owned)
  .lastReadTo(68L)
  .readingPercentage(percentageRead(68L,findBook("9789545280320").getBookLength()))
  .build();

   UserBooks Link4 = UserBooks.builder()
  .user(findUser(2L))
  .book(findBook("9789545280320"))
  .readingStatus(Status.Read)
  .lastReadTo(208L)
    .readingPercentage(percentageRead(208L,findBook("9789545280320").getBookLength()))

  .build();

   UserBooks Link5 = UserBooks.builder()
  .user(findUser(4L))
  .book(findBook("9789545280320"))
  .readingStatus(Status.Wishlisted)
  .lastReadTo(102L)
  .readingPercentage(percentageRead(102L,findBook("9789545280320").getBookLength()))
  .build();

    userBookRepo.saveAll(List.of(Link1,Link2,Link3,Link4,Link5));

     log.info("User Books succesfully seeded!");
    }
      private User findUser(Long user_id)
   {
      return userRepo.findByUserID(user_id).orElseThrow(()-> new IllegalStateException("Cannot seed User Books: User '"+ user_id +"' doesn't exist"));
   }
      private Book findBook(String ISBN)
   {
      return bookRepo.findById(ISBN).orElseThrow(()-> new IllegalStateException("Cannot seed User Books: Book with the ISBN:'"+ ISBN +"' doesn't exist"));
   }
     public BigDecimal percentageRead(long lastReadTo,long bookLenght){
      float fraction =  (float) lastReadTo/bookLenght;
      float uncleanPer = fraction*100;
      return new BigDecimal((long)uncleanPer).setScale(2,RoundingMode.HALF_UP);
  } 
} 
 