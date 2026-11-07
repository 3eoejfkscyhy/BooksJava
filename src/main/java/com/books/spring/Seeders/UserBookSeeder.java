package com.books.spring.Seeders;

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
  .bookStatus(Status.Owned)
  .build();

   UserBooks Link2 = UserBooks.builder()
  .user(findUser(3L))
  .book(findBook("9788178530857"))
  .bookStatus(Status.Rented)
  .build();

   UserBooks Link3 = UserBooks.builder()
  .user(findUser(3L))
  .book(findBook("9789545280320"))
  .bookStatus(Status.Owned)
  .build();

   UserBooks Link4 = UserBooks.builder()
  .user(findUser(2L))
  .book(findBook("9789545280320"))
  .bookStatus(Status.Timed)
  .build();

   UserBooks Link5 = UserBooks.builder()
  .user(findUser(4L))
  .book(findBook("9789545280320"))
  .bookStatus(Status.Rented)
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
}
   
