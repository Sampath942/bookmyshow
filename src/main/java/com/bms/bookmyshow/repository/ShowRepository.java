package com.bms.bookmyshow.repository;

import com.bms.bookmyshow.model.MovieShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<MovieShow, Long> {
    List<MovieShow> findByMovie_MovieIdAndLanguageAndFormat(Long movieId, String language, String format);
    Optional<MovieShow> findByShowId(Long showId);
}
