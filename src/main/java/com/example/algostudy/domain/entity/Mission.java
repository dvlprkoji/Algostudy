package com.example.algostudy.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mission {

    @Id @GeneratedValue
    private Long id;

    @Column(name="mission_name")
    private String missionName;

    @Column(name = "mission_desc")
    private String missionDesc;

    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;


}
