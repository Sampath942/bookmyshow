package com.bms.bookmyshow.services;

import com.bms.bookmyshow.model.MovieShow;
import com.bms.bookmyshow.model.MovieShowDTO;
import com.bms.bookmyshow.model.Theatre;
import com.bms.bookmyshow.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShowService {
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private TheatreService theatreService;
    @Autowired
    private TicketService ticketService;
    public List<MovieShowDTO> filterShowsBasedOnMovieIdLangAndFormat(Long movieId, String language, String format)
    {
        List<MovieShow> movieShows = showRepository.findByMovie_MovieIdAndLanguageAndFormat(movieId, language, format);
        return movieShows.stream().map(
                show -> new MovieShowDTO(
                        show.getShowId(),
                        show.getShow_time(),
                        show.getFormat(),
                        show.getLanguage(),
                        show.getTheatre().getThetareId(),
                        show.getMovie().getMovieId(),
                        show.getTheatre().getName()
                )
        ).collect(Collectors.toList());
    }
    public Map<String, Object> getSeatsStatus(Long showId) {
        Optional<MovieShow> show= showRepository.findByShowId(showId);
        Map<String, Object> seatsStatus = new HashMap<>();
        if(show.isPresent())
        {
            Theatre theatre = show.get().getTheatre();
            List<String> allSeats = theatreService.getAllSeats(theatre.getThetareId());
            List<String> unavailableSeats = ticketService.getUnavailableSeatsForShow(showId);
            for(String seatId: allSeats)
            {
                seatsStatus.put(seatId, true);
            }
            for(String seatId: unavailableSeats)
            {
                seatsStatus.put(seatId, false);
            }
            seatsStatus.put("Theatre name", theatre.getName());
        }
        return seatsStatus;
    }
}
