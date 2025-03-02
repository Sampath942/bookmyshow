package com.bms.bookmyshow.model;

import java.util.List;

public record TicketDTO (
    Long ticketId,
    String title,
    String language,
    String format,
    Long showTime,
    String location,
    List<String> seats
)
{

}