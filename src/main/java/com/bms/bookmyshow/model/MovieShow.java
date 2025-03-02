package com.bms.bookmyshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MovieShow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long showId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieId")
    private Movie movie;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatreId")
    private Theatre theatre;
    @Column(nullable = false)
    Long show_time;
    @Column(nullable = false)
    String format;
    @Column(nullable = false)
    String language;
}
