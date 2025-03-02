package com.bms.bookmyshow.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long thetareId;
    @Column(nullable = false)
    private String name;
    @ElementCollection
    private List<String> seats;
    @Column(nullable = false)
    private String location;
}
