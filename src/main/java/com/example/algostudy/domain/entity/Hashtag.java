package com.example.algostudy.domain.entity;

import javax.persistence.*;

@Entity
public class Hashtag {

    @Id @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
