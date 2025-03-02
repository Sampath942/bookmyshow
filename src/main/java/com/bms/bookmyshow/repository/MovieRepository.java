package com.bms.bookmyshow.repository;

import com.bms.bookmyshow.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAll();
    List<Movie> findMoviesByTitleContains(String title);
    Optional<Movie> findMovieByMovieId(Long movieID);
    @Query("SELECT m FROM Movie m ORDER BY m.releaseDate DESC")
    Page<Movie> findTop10MostRecentMovies(Pageable pageable);
}
