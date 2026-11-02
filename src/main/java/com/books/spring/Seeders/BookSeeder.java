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
  .isbn(9870230748392L)
  .title("Tierry Henry: Lonely at the Top")
  .bookLength(352L)
  .language("English")
  .price(new BigDecimal(2.00))
  .booksGenres(addGenres(new String[]{"Biography","Sports memoir"}))
  .bookAuthors(addAuthors(new String[][]{{"Philippe","Auclair"}}))
  .build();

    Book CwJ = Book.builder()
  .isbn(9870141019703L)
  .title("Cook with Jamie")
  .bookLength(448L)
  .language("English")
  .price(new BigDecimal(10.17))
  .booksGenres(addGenres(new String[]{"Cookbook"}))
    .bookAuthors(addAuthors(new String[][]{{"Jamie","Oliver"}}))
  .build();
 

   Book SnSS = Book.builder()
  .isbn(9789545280320L)
  .title("Sto na sto Stoichkov")
  .bookLength(208L)
  .language("Bulgarian")
  .price(new BigDecimal(15.29))
   .booksGenres(addGenres(new String[]{"Biography","Sports memoir"}))
   .bookAuthors(addAuthors(new String[][]{{"Hristo","Stoichkov"},{"Philippe","Auclair"}}))
  .build();



  Book CC = Book.builder()
  .isbn(9788178530857L)
  .title("Code Complete")
  .bookLength(960L)
  .language("English")
  .price(new BigDecimal(47.99))
  .booksGenres(addGenres(new String[]{"Technical non-fiction"}))
  .bookAuthors(addAuthors(new String[][]{{"Steve"}}))
  .build();


    bookRepo.saveAll(List.of(THLatT,CwJ,SnSS,CC));

     log.info("Books succesfully seeded!");
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
        else if(authorList.length==1)
    tempAuthor.add(authorRepo.findByfirstName(givenAuthor[0]).orElseThrow(()-> new IllegalStateException("Cannot seed Books: Author '"+ givenAuthor[0] +"' doesn't exist")));
        else continue;
    }
    return tempAuthor;
}
   
}