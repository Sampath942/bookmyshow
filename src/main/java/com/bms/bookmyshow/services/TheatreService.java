package com.bms.bookmyshow.services;

import com.bms.bookmyshow.model.Theatre;
import com.bms.bookmyshow.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TheatreService {
    @Autowired
    private TheatreRepository theatreRepository;
    public List<String> getAllSeats(Long theatreId)
    {
        Optional<Theatre> theatre = theatreRepository.findByThetareId(theatreId);
        if(theatre.isPresent())
        {
            return theatre.get().getSeats();
        }
        return new ArrayList<>();
    }
}
