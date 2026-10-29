package com.books.spring.Seeders;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.books.spring.Models.User;

import com.books.spring.Repositories.UserRepo;


import  lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;


@Slf4j
@Component 
@RequiredArgsConstructor

public class UserSeeder implements CommandLineRunner {
     private final UserRepo userRepo;
     @Override
     public void run(String... args) throws Exception  {
        if(userRepo.count()>0)
        {
            log.info("oefnn");
            return;  
        }

     User Jones = User.builder()
     .username("Mdkcs")
     .email("Hops@cjd.ru")
     .status("Killyu")
     .balance(new BigDecimal(2.30))
     .build();


        User Skips = User.builder()
     .username("dkcmIi")
     .email("Husoc@gidm.brs")
     .status("ahahah")
     .balance(new BigDecimal(10.30))
     .build();

         userRepo.saveAll(List.of(Jones,Skips));
     
     log.info("Done");
   }
}

