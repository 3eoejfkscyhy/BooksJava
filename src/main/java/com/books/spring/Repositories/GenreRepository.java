package com.books.spring.Repositories;

import com.books.spring.Models.Genre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface GenreRepository extends JpaRepository<Genre,Long> {

   Optional<Genre> findByGenreName(String genreName);
   Optional<Genre> findByGenreID(Long genreID);

   boolean existsByGenreNameIgnoreCase(String genreName);
   boolean existsByGenreName(String genreName);
}
