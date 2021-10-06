package com.example.algostudy.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String email;

    private String password;

    @Column(name = "boj_id")
    private String bojId;

    @Column(name = "oauth2_id")
    private String oAuth2Id;

    @OneToMany(mappedBy = "member")
    private List<MemberRole> memberRoleList;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
