package com.books.spring.Misc;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.books.spring.Models.*;
import com.books.spring.Repositories.BookRepository;
import com.books.spring.Repositories.UserRepository;
import com.books.spring.Exception.*;
import com.books.spring.Misc.BooksJavaConstants.ExceptionMessages;

public class BooksJavaFunctions {
     public static BigDecimal percentageRead(long lastReadTo,long bookLenght){
      float fraction =  (float) lastReadTo/bookLenght;
      float uncleanPer = fraction*100;
      return new BigDecimal((long)uncleanPer).setScale(2,RoundingMode.HALF_UP);
     }


      public static Book findBook(BookRepository bookRepository, String isbn) {
        return bookRepository.findById(isbn)
                .filter(book -> !book.getIsDeleted())
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.GIVEN_BOOK_NOT_FOUND.formatted(isbn)));
    }
    
    public static User findUser(UserRepository userRepository, Long userID) {
        return userRepository.findByUserID(userID)
                .filter(user -> !user.getIsDeleted())
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
  

