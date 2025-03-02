package com.bms.bookmyshow;

import com.bms.bookmyshow.model.Movie;
import com.bms.bookmyshow.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class MovieAdderTest {
    @Autowired
    private MovieRepository movieRepository;
     @Test
    public void addMovies() {
         List<String> languages = new LinkedList<>();
         languages.add("Hindi");
         languages.add("Telugu");
         languages.add("Tamil");
         List<String> formats = new LinkedList<>();
         formats.add("2D");
         formats.add("3D");
         for(int i = 0; i < 10; i++)
         {
             Movie movie = new Movie();
             movie.setTitle("Movie" + i);
             movie.setDescription("Kattappa kills Bahubali");
             movie.setLanguages(languages);
             movie.setFormats(formats);
             movie.setReleaseDate(Date.valueOf("2024-12-06"));
             movieRepository.save(movie);

         }
     }
}
