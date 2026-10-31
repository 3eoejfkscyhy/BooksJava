package com.books.spring.Seeders;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import com.books.spring.Models.Collection;

import com.books.spring.Models.User;

import com.books.spring.Repositories.CollectionRepository;
import com.books.spring.Repositories.UserRepository;
import  lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j 
@Component 
@RequiredArgsConstructor
@Order(3)
public class CollectionSeeder implements CommandLineRunner{
    private final CollectionRepository collectionRepo;
    private final UserRepository userRepo;
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
  .build();

    Collection MB = Collection.builder()
  .collectionName("My Books")
  .user(findUser(1L))
  .build();

   Collection UC = Collection.builder()
  .collectionName("Unnamed Collection")
  .user(findUser(1L))
  .build();

   Collection I = Collection.builder()
  .collectionName("Info")
  .user(findUser(4L))
  .build();

    collectionRepo.saveAll(List.of(FM,MB,UC,I));

     log.info("Collections succesfully seeded!");
    }
       private User findUser(Long user_id)
   {
      return userRepo.findByUserID(user_id).orElseThrow(()-> new IllegalStateException("Cannot seed Reviews: user don't exits"));
   }
   
}
