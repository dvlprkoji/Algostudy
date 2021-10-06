package com.example.algostudy.domain.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Team {

    @Id @GeneratedValue
    private Long id;

    private String teamName;
    private String teamDesc;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Member> memberList;

}
