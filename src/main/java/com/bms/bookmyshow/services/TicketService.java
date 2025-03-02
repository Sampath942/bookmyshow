package com.bms.bookmyshow.services;

import com.bms.bookmyshow.model.MovieShow;
import com.bms.bookmyshow.model.Ticket;
import com.bms.bookmyshow.model.TicketDTO;
import com.bms.bookmyshow.model.User;
import com.bms.bookmyshow.repository.ShowRepository;
import com.bms.bookmyshow.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private ShowRepository showRepository;
    public List<String> getUnavailableSeatsForShow(Long showId)
    {
        List<Ticket> tickets = ticketRepository.findAllByMovieShow_ShowId(showId);
        List<String> unavailableSeats = new ArrayList<>();
        for(Ticket ticket: tickets)
        {
            unavailableSeats.addAll(ticket.getSeats());
        }
        return unavailableSeats;
    }
    public TicketDTO bookTicket(Map<String, Object> details, String jwt)
    {
        long showId = Long.valueOf((Integer) details.get("showId"));

        List<String> seats = (List<String>) details.get("seats");
        Optional<User> user = myUserDetailService.getUser(jwt);
        Optional<MovieShow> movieShow = showRepository.findByShowId(showId);
        if(user.isPresent() && movieShow.isPresent())
        {
            User myUser = user.get();
            MovieShow show = movieShow.get();
            int price = seats.size() * 100;
            Ticket ticket = new Ticket();
            ticket.setSeats(seats);
            ticket.setPrice(price);
            ticket.setMovieShow(show);
            ticket.setUser(myUser);
            ticketRepository.save(ticket);
            return new TicketDTO(ticket.getTicketId(), show.getMovie().getTitle(), show.getLanguage(), show.getFormat(), show.getShow_time(), show.getTheatre().getLocation(), seats);
        }
        else {
            return null;
        }
    }
}
