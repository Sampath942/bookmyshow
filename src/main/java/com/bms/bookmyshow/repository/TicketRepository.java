package com.bms.bookmyshow.repository;

import com.bms.bookmyshow.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository <Ticket, Long>{
    public List<Ticket> findAllByMovieShow_ShowId(Long showId);
}
