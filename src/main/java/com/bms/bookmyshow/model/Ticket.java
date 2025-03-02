package com.bms.bookmyshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ticketId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showId")
    private MovieShow movieShow;
    @Column(nullable = false)
    private Integer price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User user;
    @ElementCollection
    private List<String> seats;
}
