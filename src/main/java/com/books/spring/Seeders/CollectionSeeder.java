package com.books.spring.Seeders;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.books.spring.Models.Collection;
import com.books.spring.Models.Book;
import com.books.spring.Models.User;
import com.books.spring.Repositories.BookRepository;
import com.books.spring.Repositories.CollectionRepository;
import com.books.spring.Repositories.UserRepository;
import  lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Component 
@RequiredArgsConstructor
@Order(6)
public class CollectionSeeder implements CommandLineRunner{
    private final CollectionRepository collectionRepo;
    private final UserRepository userRepo;
    private final BookRepository bookRepo;
    @Override 
    public void run(String... args) throws Exception
    {
        if(collectionRepo.count()>0)
        {
           log.info("Collections already exists. Skipping seeding collections.");
            return;
        }
    
  Collection FM = Collection.builder()
  .collectionName("Football Memoirs")
  .user(findUser(2L))
  .books(addBooks(new Long[]{9870230748392L,9789545280320L}))
  .build();

    Collection MB = Collection.builder()
  .collectionName("My Books")
  .user(findUser(1L))
  .books(addBooks(new Long[]{9870230748392L,9789545280320L,9788178530857L,9870141019703L}))
  .build();

   Collection UC = Collection.builder()
  .collectionName("Unnamed Collection")
  .user(findUser(1L))
  .build();

   Collection I = Collection.builder()
  .collectionName("Info")
   .books(addBooks(new Long[]{9788178530857L}))
  .user(findUser(4L))
  .build();

    collectionRepo.saveAll(List.of(FM,MB,UC,I));

     log.info("Collections succesfully seeded!");
    }
       private User findUser(Long user_id)
   {
        return userRepo.findByUserID(user_id).orElseThrow(()-> new IllegalStateException("Cannot seed Collections: User '"+ user_id +"' doesn't exist"));
   }

      private Set<Book> addBooks(Long[] bookList)
   {
    Set <Book> tempBook = new HashSet<>();
    for (Long givenBook: bookList) {
    tempBook.add(bookRepo.findByisbn(givenBook).orElseThrow(()-> new IllegalStateException("Cannot seed Collections: Book with the ISBN '"+ givenBook +"' doesn't exist")));
    }
    return tempBook;
   }
   
}
