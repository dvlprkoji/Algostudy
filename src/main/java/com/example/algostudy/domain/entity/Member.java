package com.example.algostudy.domain.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<InviteTeamMember> inviteTeamMemberList;

    @OrderBy("sendDateTime desc")
    @OneToMany(mappedBy = "member")
    private List<Message> messageQueue = new LinkedList<>();

//    @Transactional
//    public List<Role> getRoleList(){
//        return memberRoleList.stream().map(MemberRole::getRole).collect(Collectors.toList());
//    }

}
