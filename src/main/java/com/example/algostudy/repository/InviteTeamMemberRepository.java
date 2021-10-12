package com.example.algostudy.repository;

import com.example.algostudy.domain.entity.InviteTeamMember;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteTeamMemberRepository extends JpaRepository<InviteTeamMember, Long> {

    InviteTeamMember findByTeamAndMember(Team team, Member findMember);
}
