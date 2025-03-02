package com.bms.bookmyshow.controller;

import com.bms.bookmyshow.model.*;
import com.bms.bookmyshow.services.MovieService;
import com.bms.bookmyshow.services.ShowService;
import com.bms.bookmyshow.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private ShowService showService;
    @Autowired
    private TicketService ticketService;
    @GetMapping("/movies/search/{title}")
    public ResponseEntity<Map<String,Object>> searchMoviesByTitle(@PathVariable String title)
    {
        Map<String,Object> response = new HashMap<>();
        response.put("Movies", movieService.loadMoviesByTitle(title));
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }
    @GetMapping("/movies/{movieID}")
    public ResponseEntity<Optional<Movie>> searchMovieById(@PathVariable long movieID)
    {
        Optional<Movie> movie = movieService.loadMoviesByMovieID(movieID);
        return new ResponseEntity<Optional<Movie>>(movie, HttpStatus.OK);
    }
    @GetMapping("/movies/shows")
    public ResponseEntity<List<MovieShowDTO>> filterShowsByMovieIdLangFormat(
            @RequestParam Long movieId,
            @RequestParam String language,
                    @RequestParam String format
    )
    {
        return new ResponseEntity<List<MovieShowDTO>> (showService.filterShowsBasedOnMovieIdLangAndFormat(movieId, language, format), HttpStatus.OK);
    }

    @GetMapping("/movies/shows/seats")
    public ResponseEntity<Map<String,Object>> getSeatsAvailability(@RequestParam Long showId)
    {
        return new ResponseEntity<Map<String,Object>>(showService.getSeatsStatus(showId), HttpStatus.OK);
    }
    @PostMapping("/movies/shows/book")
    public ResponseEntity<?> bookTicket(
            @RequestBody Map<String, Object> details,
                                            @RequestHeader("Authorization") String authHeader)
    {
//        Map<String, Object> details = new HashMap<>();
//        details.put("showId", (Object) 2L);
//        List<String> seats = new ArrayList<>();
//        seats.add("D1");
//        details.put("seats", (Object) seats);
        String jwt=authHeader.substring(7);
        TicketDTO ticket = ticketService.bookTicket(details, jwt);
        if(ticket != null)
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Invalid user or show id");
    }
}
