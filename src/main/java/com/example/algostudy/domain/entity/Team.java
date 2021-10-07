package com.example.algostudy.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String teamName;
    private String teamDesc;
    private String status;
    private String imagePath;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String studyUrl;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Hashtag> hashtagList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Member> memberList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mission> missionList;
}
