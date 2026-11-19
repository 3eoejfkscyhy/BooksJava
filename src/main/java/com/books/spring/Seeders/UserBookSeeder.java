package com.books.spring.Seeders;

import  java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.books.spring.Misc.BooksJavaFunctions;
import com.books.spring.Misc.BooksJavaConstants.LogMessages;
import com.books.spring.Misc.BooksJavaConstants.SeederOrder;
import com.books.spring.Models.UserBooks;
import com.books.spring.Models.UserBooks.Status;
import com.books.spring.Repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j 
@Component 
@RequiredArgsConstructor
@Order(SeederOrder.USER_BOOK)
public class UserBookSeeder implements CommandLineRunner{
    private final BookRepository bookRepo;
    private final UserRepository userRepo;
    private final UserBookRepository userBookRepo;
    @Override 
    public void run(String... args) throws Exception
    {
        if(userBookRepo.count()>0)
        {
             log.info(LogMessages.USER_BOOK_ALREADY_EXISTS);
            return;
        }
 
   UserBooks Link1 = UserBooks.builder()
  .user(BooksJavaFunctions.findUserSeeder(userRepo,1L))
  .book(BooksJavaFunctions.findBookSeeder(bookRepo,"9788178530857"))
  .readingStatus(Status.Owned)
  .lastReadTo(30L)
  .readingPercentage(BooksJavaFunctions.percentageRead(30L,BooksJavaFunctions.findBookSeeder(bookRepo,"9788178530857").getBookLength()))
  .build();

   UserBooks Link2 = UserBooks.builder()
  .user(BooksJavaFunctions.findUserSeeder(userRepo,3L))
  .book(BooksJavaFunctions.findBookSeeder(bookRepo,"9788178530857"))
  .readingStatus(Status.Wishlisted)
  .lastReadTo(0L)
 .readingPercentage(BooksJavaFunctions.percentageRead(0L,BooksJavaFunctions.findBookSeeder(bookRepo,"9788178530857").getBookLength()))
  .build();

   UserBooks Link3 = UserBooks.builder()
  .user(BooksJavaFunctions.findUserSeeder(userRepo,3L))
  .book(BooksJavaFunctions.findBookSeeder(bookRepo,"9789545280320")) 
  .readingStatus(Status.PreviouslyOwned)
  .lastReadTo(68L)
  .readingPercentage(BooksJavaFunctions.percentageRead(68L,BooksJavaFunctions.findBookSeeder(bookRepo,"9789545280320").getBookLength()))
  .build();

   UserBooks Link4 = UserBooks.builder()
  .user(BooksJavaFunctions.findUserSeeder(userRepo,2L))
  .book(BooksJavaFunctions.findBookSeeder(bookRepo,"9789545280320"))
  .readingStatus(Status.Read)
  .lastReadTo(208L)
    .readingPercentage(BooksJavaFunctions.percentageRead(208L,BooksJavaFunctions.findBookSeeder(bookRepo,"9789545280320").getBookLength()))

  .build();

   UserBooks Link5 = UserBooks.builder()
  .user(BooksJavaFunctions.findUserSeeder(userRepo,4L))
  .book(BooksJavaFunctions.findBookSeeder(bookRepo,"9789545280320"))
  .readingStatus(Status.Wishlisted)
  .lastReadTo(102L)
  .readingPercentage(BooksJavaFunctions.percentageRead(102L,BooksJavaFunctions.findBookSeeder(bookRepo,"9789545280320").getBookLength()))
  .build();

    userBookRepo.saveAll(List.of(Link1,Link2,Link3,Link4,Link5));

     log.info(LogMessages.USER_BOOK_SEEDED);
    }
} 
 