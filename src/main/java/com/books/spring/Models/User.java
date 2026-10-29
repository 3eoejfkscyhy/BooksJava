package com.books.spring.Models;
import java.math.BigDecimal;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
@Builder 
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID; //Use for Seeder

    @Column(name = "username", nullable = false)
    private String username; //Use for Seeder

    @Column(name = "email", nullable = false)
    private String email; //Use for Seeder

    @Column(name = "status")
    private String status; //Use for Seeder

    @Column(name = "balance")
    private BigDecimal balance;  //Use for Seeder

    @Builder.Default
    @OneToMany(mappedBy = "writtenBy")
    private Set<Review> reviews = new HashSet<>();
    //Ask about Sets in Seeder

    @Builder.Default
    @OneToMany(mappedBy = "user",orphanRemoval = true)
    private Set<Collection> collections = new HashSet<>();
      //Ask about Sets in Seeder

}