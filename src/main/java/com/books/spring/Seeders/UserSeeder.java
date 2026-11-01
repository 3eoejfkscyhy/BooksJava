package com.books.spring.Seeders;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.books.spring.Models.User;
import com.books.spring.Repositories.UserRepository;
import  lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;


@Slf4j
@Component 
@RequiredArgsConstructor
@Order(1)
public class UserSeeder implements CommandLineRunner {
     private final UserRepository userRepo;
     @Override
     public void run(String... args) throws Exception  {
        if(userRepo.count()>0)
        {
            log.info("Users already exists. Skipping seeding users.");
            return;  
        }

     User Mike = User.builder()
     .username("MikeMike1020")
     .email("MikeMikeMike10@Hotmail.com")
     .password("W1deEyedMike")
     .status("")
     .balance(new BigDecimal(0.00))
     .build();


        User Antony = User.builder()
     .username("AntonyReal")
     .email("FutBallG0at@yahoo.com")
     .password("RealDiaB3tis")
     .status("I am a smart dude, I can read the pitch")
     .balance(new BigDecimal(10.30))
     .build();

      User Max = User.builder()
     .username("ChefMaxGuye")
     .email("MaxTheC00k@gmail.com")
     .password("lowandslow")
     .status("I love to cook many foods, my favourite type is french")
     .balance(new BigDecimal(2.10))
     .build();

        User Doug = User.builder()
     .username("DougDougDougDoug")
     .email("DouglasDoglas3@aol.com")
     .password("Hiitsbillymayswithzorbees")
     .status("Currently reading Twitch for dummies!")
     .balance(new BigDecimal(23.33))
     .build();

         userRepo.saveAll(List.of(Mike,Antony,Max,Doug));
     
     log.info("Users successfully seeded!");
   }
}

