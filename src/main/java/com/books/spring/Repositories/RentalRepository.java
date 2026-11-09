package com.books.spring.Repositories;

import com.books.spring.Models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("""
           SELECT COUNT(r) > 0
           FROM Rental r
           WHERE r.user.userID = :userID
           AND r.book.isbn = :isbn
           AND r.status = :status
           """)
    boolean existsActiveRental(@Param("userID") Long userID,
                         @Param("isbn") String isbn,
                         @Param("status") Rental.RentalStatus status);
}