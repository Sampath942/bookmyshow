package com.bms.bookmyshow;

import com.bms.bookmyshow.model.Movie;
import com.bms.bookmyshow.model.MovieShow;
import com.bms.bookmyshow.model.Theatre;
import com.bms.bookmyshow.repository.MovieRepository;
import com.bms.bookmyshow.repository.ShowRepository;
import com.bms.bookmyshow.repository.TheatreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ShowAdderTest {
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Test
    public void addShows() {
        for(int i = 0; i < 10; i++)
        {
            MovieShow show = new MovieShow();
            show.setFormat("2D");
            show.setLanguage("Telugu");
            show.setShow_time(12345678L);
            Optional<Movie> opMovie = movieRepository.findMovieByMovieId((long) ((i % 4) + 1));
            if(opMovie.isEmpty())
                continue;
            show.setMovie(opMovie.get());
            Optional<Theatre> opTheatre = theatreRepository.findByThetareId((long) ((i % 4) + 1));
            if(opTheatre.isEmpty())
                continue;;
            show.setTheatre(opTheatre.get());
            showRepository.save(show);
        }
    }
}
