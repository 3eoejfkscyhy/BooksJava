package com.books.spring.Models;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter
@Setter
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "writtenBy")
    private Review reviews;

    @OneToMany(mappedBy = "user",orphanRemoval = true)
    private Set<Collection> collections = new HashSet<>();

}