package com.bms.bookmyshow.services;

import com.bms.bookmyshow.model.Movie;
import com.bms.bookmyshow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    public List<Movie> loadMovies() {
        return movieRepository.findAll();
    }
    public List<Movie> loadMoviesByTitle(String title) {
        return movieRepository.findMoviesByTitleContains(title);
    }
    public Optional<Movie> loadMoviesByMovieID(long movieID) {
        return movieRepository.findMovieByMovieId(movieID);
    }
    public List<Movie> getTop10MostRecentMovies() {
        // Create a Pageable object for the top 10
        PageRequest pageable = PageRequest.of(0, 10);

        // Fetch movies using the repository
        Page<Movie> moviesPage = movieRepository.findTop10MostRecentMovies(pageable);

        return moviesPage.getContent();
    }
}
