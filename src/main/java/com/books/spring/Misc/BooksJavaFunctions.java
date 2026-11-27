package com.books.spring.Misc;

import com.books.spring.Models.*;
import com.books.spring.Repositories.BookRepository;
import com.books.spring.Repositories.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.books.spring.DTOs.*;
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

     public static BookDTO book(String isbn, String title,Long bookLenght,String language,Book.BookType bookType,BigDecimal price, BigDecimal rentalPrice,Long[] authorIDs, Long[] reviewIDs,Long[] genreIDs,Long[] collectionIDs,LocalDateTime freeFrom,LocalDateTime freeUntil) {
        Set<Long> authorIds = new HashSet<>();
        for (Long authorId : authorIDs) {
            authorIds.add(authorId);
        }
         Set<Long> reviewIds = new HashSet<>();
        for (Long reviewId : reviewIDs) {
            reviewIds.add(reviewId);
        }
         Set<Long> genreIds = new HashSet<>();
        for (Long genreId : genreIDs) {
            genreIds.add(genreId);
        }
         Set<Long> collectionIds = new HashSet<>();
        for (Long collectionId : collectionIDs) {
            collectionIds.add(collectionId);
        }
        return new BookDTO(isbn, title, bookLenght, language, bookType, price,rentalPrice,authorIds,reviewIds,genreIds,collectionIds,freeFrom,freeUntil);
    }


    public static ReviewDTO review(Long reviewID, String headline,String body,BigDecimal stars, String isbn,Long userID,Boolean isEdited) {
        return new ReviewDTO(reviewID, headline,body,stars, isbn,userID,LocalDate.now(),isEdited);
    }

        public static ReviewChangeDTO reviewChange(String headline,String body,BigDecimal stars, String isbn,Long userID) {
        return new ReviewChangeDTO(headline,body,stars, isbn,userID);
    }


     public  static UserDTO user(Long userID, String username,String email, String password, String status,BigDecimal balance,Long[] reviewIDs,Long[] collectionIDs) {

        Set<Long> reviewIds = new HashSet<>();
        for (Long reviewId : reviewIDs) {
            reviewIds.add(reviewId);
        }
         Set<Long> collectionIds = new HashSet<>();
        for (Long collectionId : collectionIDs) {
            collectionIds.add(collectionId);
        }
        return new UserDTO(userID, username,email,password,status,balance,reviewIds,collectionIds);
    }
  
} 
  

