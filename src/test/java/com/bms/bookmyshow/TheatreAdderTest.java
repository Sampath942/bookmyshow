package com.bms.bookmyshow;

import com.bms.bookmyshow.model.Theatre;
import com.bms.bookmyshow.repository.TheatreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;


@SpringBootTest
public class TheatreAdderTest {
    @Autowired
    private TheatreRepository theatreRepository;
    @Test
    public void addTheatres() {
        List<String> seats = new LinkedList<>();
        for(int i=0; i< 10; i++)
        {
            for(int j=0;j<10;j++)
            {
                String s = "";
                char u = (char) ('A' + i);
                s = s + u;
                s = s + (j + 1);
                seats.add(s);
            }
        }
        for(int i = 0; i < 10; i++)
        {
            Theatre theatre = new Theatre();
            theatre.setName("theatre_"+i);
            theatre.setLocation("Vizag");

            theatre.setSeats(seats);
            theatreRepository.save(theatre);
        }
    }
}
