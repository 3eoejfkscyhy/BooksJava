package com.books.spring.Seeders;

import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.books.spring.Models.Collection;
import com.books.spring.Misc.BooksJavaConstants.LogMessages;
import com.books.spring.Misc.BooksJavaConstants.SeederOrder;
import com.books.spring.Misc.BooksJavaFunctions;
import com.books.spring.Models.Book;
import com.books.spring.Repositories.*;
import  lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Component 
@RequiredArgsConstructor
@Order(SeederOrder.COLLECTION)
public class CollectionSeeder implements CommandLineRunner{
    private final CollectionRepository collectionRepo;
    private final UserRepository userRepo;
    private final BookRepository bookRepo;
    @Override 
    public void run(String... args) throws Exception
    {
        if(collectionRepo.count()>0)
        {
           log.info(LogMessages.COLLECTION_ALREADY_EXISTS);
            return;
        }
    
  Collection FM = Collection.builder()
  .collectionName("Football Memoirs")
  .user(BooksJavaFunctions.findUserSeeder(userRepo,2L))
  .books(addBooks(new String[]{"9780230748392", "9789545280320"}))
  .build();

    Collection MB = Collection.builder()
  .collectionName("My Books")
  .user(BooksJavaFunctions.findUserSeeder(userRepo,1L))
  .books(addBooks(new String[]{"9780230748392", "9789545280320", "9788178530857", "9780141019703"}))
  .build();

   Collection UC = Collection.builder()
  .collectionName("Unnamed Collection")
  .user(BooksJavaFunctions.findUserSeeder(userRepo,1L))
  .build();

   Collection I = Collection.builder()
  .collectionName("Info")
   .books(addBooks(new String[]{"9788178530857"}))
  .user(BooksJavaFunctions.findUserSeeder(userRepo,4L))
  .build();

    collectionRepo.saveAll(List.of(FM,MB,UC,I));

     log.info(LogMessages.COLLECTION_SEEDED);
    }

      private Set<Book> addBooks(String[] bookList)
   {
    Set <Book> tempBook = new HashSet<>();
    for (String givenBook: bookList) {
    tempBook.add(bookRepo.findById(givenBook).orElseThrow(()-> new IllegalStateException("Cannot seed Collections: Book with the ISBN '"+ givenBook +"' doesn't exist")));
    }
    return tempBook;
   }
   
}
