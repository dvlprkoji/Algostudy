package com.example.algostudy.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String teamName;
    private String teamDesc;
    private String status;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @OneToOne
    private Image mainImage;

    private String studyUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private List<Hashtag> hashtagList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private List<Member> memberList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private List<Mission> missionList;
}
