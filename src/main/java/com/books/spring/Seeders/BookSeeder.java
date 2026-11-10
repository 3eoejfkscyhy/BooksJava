package com.books.spring.Seeders;

import java.math.BigDecimal;
import  java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.books.spring.Models.Author;
import com.books.spring.Models.Book;
import com.books.spring.Models.Genre;
import com.books.spring.Repositories.AuthorRepository;
import com.books.spring.Repositories.BookRepository;
import com.books.spring.Repositories.GenreRepository;
import  lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j 
@Component 
@RequiredArgsConstructor
@Order(4)
public class BookSeeder implements CommandLineRunner{
    private final BookRepository bookRepo;
    private final GenreRepository genreRepo;
    private final AuthorRepository authorRepo;
    @Override 
    public void run(String... args) throws Exception
    {
        if(bookRepo.count()>0)
        {
             log.info("Books already exists. Skipping seeding books.");
            return;
        }
  Book THLatT = Book.builder()
  .isbn("9870230748392")
  .title("Tierry Henry: Lonely at the Top")
  .bookLength(352L)
  .language("English")
  .price(new BigDecimal("2.00"))
  .bookGenres(addGenres(new String[]{"Biography","Sports memoir"}))
  .bookAuthors(addAuthors(new String[][]{{"Philippe","Auclair"}}))
          .bookType(Book.BookType.EBook)
          .rentalPrice(new BigDecimal("1.00"))
          .build();

    Book CwJ = Book.builder()
  .isbn("9870141019703")
  .title("Cook with Jamie")
  .bookLength(448L)
  .language("English")
  .price(new BigDecimal("10.17"))
  .bookGenres(addGenres(new String[]{"Cookbook"}))
    .bookAuthors(addAuthors(new String[][]{{"Jamie","Oliver"}}))
            .bookType(Book.BookType.PhysicalBook)
            .rentalPrice(null)
  .build();
 

   Book SnSS = Book.builder()
  .isbn("9789545280320")
  .title("Sto na sto Stoichkov")
  .bookLength(208L)
  .language("Bulgarian")
  .price(new BigDecimal("15.29"))
   .bookGenres(addGenres(new String[]{"Biography","Sports memoir"}))
   .bookAuthors(addAuthors(new String[][]{{"Hristo","Stoichkov"},{"Philippe","Auclair"}}))
           .bookType(Book.BookType.EBook)
           .rentalPrice(new BigDecimal("10.00"))
  .build();



  Book CC = Book.builder()
  .isbn("9788178530857")
  .title("Code Complete")
  .bookLength(960L)
  .language("English")
  .price(new BigDecimal("47.99"))
  .bookGenres(addGenres(new String[]{"Technical non-fiction"}))
  .bookAuthors(addAuthors(new String[][]{{"Steve"}}))
          .bookType(Book.BookType.EBook)
          .rentalPrice(new BigDecimal("15.99"))
  .build();


    bookRepo.saveAll(List.of(THLatT,CwJ,SnSS,CC));

     log.info("Books successfully seeded!");
    }
   private Set<Genre> addGenres(String[] genreList)
   {
    Set <Genre> tempGenre = new HashSet<>();
    for (String givenGenre: genreList) {
    tempGenre.add(genreRepo.findBygenreName(givenGenre).orElseThrow(()-> new IllegalStateException("Cannot seed Books: Genre '"+ givenGenre +"' doesn't exist")));
    }
    return tempGenre;
   }
     private Set<Author> addAuthors(String[][] authorList)
   {
    
    Set <Author> tempAuthor = new HashSet<>();
    for (String[] givenAuthor: authorList) {
        if(givenAuthor.length==2)
    tempAuthor.add(authorRepo.findByfirstNameAndLastName(givenAuthor[0],givenAuthor[1]).orElseThrow(()-> new IllegalStateException("Cannot seed Books: Author '"+ givenAuthor[0] +" "+givenAuthor[1]+"' doesn't exist")));
        else if(givenAuthor.length==1)
    tempAuthor.add(authorRepo.findByfirstName(givenAuthor[0]).orElseThrow(()-> new IllegalStateException("Cannot seed Books: Author '"+ givenAuthor[0] +"' doesn't exist")));
        else continue;
    }
    return tempAuthor;
}
   
}