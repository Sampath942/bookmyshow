package com.bms.bookmyshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long movieId;
    @Column(nullable = false)
    private String title;
    private String description;
    @ElementCollection
    private List<String> castAndCrew;
    @Column(columnDefinition = "DECIMAL(3, 1) DEFAULT 0")
    private Float rating;
    @ElementCollection
    private List<String> languages;
    @ElementCollection
    private List<String> formats;
    @Column(nullable = false)
    private Date releaseDate;
}
