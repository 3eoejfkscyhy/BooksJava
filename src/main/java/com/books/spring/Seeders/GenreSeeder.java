package com.books.spring.Seeders;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.books.spring.Models.Genre;
import com.books.spring.Repositories.GenreRepository;
import  lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j 
@Component 
@RequiredArgsConstructor
@Order(2)
public class GenreSeeder implements CommandLineRunner{
    private final GenreRepository genreRepo;
    @Override 
    public void run(String... args) throws Exception
    {
        if(genreRepo.count()>0)
        {
             log.info("Genres already exists. Skipping seeding genres.");
            return;
        }
    
  Genre TnT = Genre.builder()
  .genreName("Technical non-fiction")
  .build();

    Genre C = Genre.builder()
  .genreName("Cookbook")
  .build();

  Genre B = Genre.builder()
  .genreName("Biography")
  .build();

  Genre SM = Genre.builder()
  .genreName("Sports memoir")
  .build();

    genreRepo.saveAll(List.of(TnT,C,B,SM));

     log.info("Genres succesfully seeded!");
    }
   
}
