package com.bms.bookmyshow;

import com.bms.bookmyshow.model.Ticket;
import com.bms.bookmyshow.repository.MyUserRepository;
import com.bms.bookmyshow.repository.ShowRepository;
import com.bms.bookmyshow.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TicketAdderTest {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private MyUserRepository myUserRepository;
    @Test
    public void addTickets(){
        List<String> seats = new ArrayList<>();
        for(int j = 0; j < 10; j += 2)
        {
            for(int k = 0; k < 10; k += 2)
            {
                String s = "";
                char u = (char) ('A' + j);
                s = s + u;
                s = s + (k + 1);
                seats.add(s);
            }
        }
        for(int i = 0; i < 10; i++)
        {
            Ticket ticket = new Ticket();
            ticket.setMovieShow(showRepository.getById((long) (i + 1)));
            ticket.setPrice(100);
            ticket.setSeats(seats);
            ticket.setUser(myUserRepository.getById((long) (i + 1)));
            ticketRepository.save(ticket);
        }
    }
}
