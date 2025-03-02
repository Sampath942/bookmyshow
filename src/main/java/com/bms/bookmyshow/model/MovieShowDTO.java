package com.bms.bookmyshow.model;

public record MovieShowDTO(Long showId,
                           Long show_time,
                           String format,
                           String language,
                           Long theatreId,
                           Long movieId,
                           String theatreName
) {
}
