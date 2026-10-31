package com.books.spring.Seeders;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import com.books.spring.Models.Author;


import com.books.spring.Repositories.AuthorRepository;
import  lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j 
@Component 
@RequiredArgsConstructor
@Order(3)
public class AuthorSeeder implements CommandLineRunner{
    private final AuthorRepository authorRepo;
    @Override 
    public void run(String... args) throws Exception
    {
        if(authorRepo.count()>0)
        {
            log.info("Authors already exists. Skipping seeding authors.");
            return;
        }
    
  Author Philippe = Author.builder()
  .firstName("Philippe")
  .lastName("Auclair")
  .age(LocalDate.of(1959,6,24))
  .booksWritten(32)
  .build();

  Author Jamie = Author.builder()
  .firstName("Jamie")
  .lastName("Oliver")
  .age(LocalDate.of(1975,5,27))
  .booksWritten(284)
  .build();

    Author Steve = Author.builder()
  .firstName("Steve")
  .lastName("McConnell")
  .age(LocalDate.of(1962,9,3))
  .booksWritten(24)
  .build();

    Author Stoichkov = Author.builder()
  .firstName("Hristo")
  .lastName("Stoichkov")
  .age(LocalDate.of(1966,2,8))
  .booksWritten(4)
  .build();

    authorRepo.saveAll(List.of(Philippe,Jamie,Steve,Stoichkov));

    log.info("Authors succesfully seeded!");
    }
}
