package com.books.spring.Misc;

import com.books.spring.Models.*;
import com.books.spring.Repositories.BookRepository;
import com.books.spring.Repositories.UserRepository;
import com.books.spring.Exception.*;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;


public class BooksJavaFunctions {
     public static String percentageRead(long lastReadTo,long bookLenght){
      float fraction =  (float) lastReadTo/bookLenght;
      return String.format("%.2f",  fraction*100) +"%"; 
     }


      public static Book findBook(BookRepository bookRepository, String isbn) {
        
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.GIVEN_BOOK_NOT_FOUND.formatted(isbn)));
         }
       
    
    public static User findUser(UserRepository userRepository, Long userID) {
        return userRepository.findByUserID(userID)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.GIVEN_USER_NOT_FOUND.formatted(userID)));
    } 


    public static  Book findBookSeeder(BookRepository bookRepository, String ISBN)
   {
      return bookRepository.findById(ISBN).orElseThrow(()-> new IllegalStateException("Cannot seed User Books: Book with the ISBN:'"+ ISBN +"' doesn't exist"));
   }

     public static User findUserSeeder(UserRepository userRepository, Long user_id)
   {
      return userRepository.findByUserID(user_id).orElseThrow(()-> new IllegalStateException("Cannot seed Reviews: User '"+ user_id +"' doesn't exist"));
   }

  
} 
  

