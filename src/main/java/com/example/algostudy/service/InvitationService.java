package com.example.algostudy.service;

import com.example.algostudy.domain.entity.InvitationMessage;
import com.example.algostudy.domain.entity.InviteTeamMember;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.Team;
import com.example.algostudy.repository.InviteTeamMemberRepository;
import com.example.algostudy.repository.Member.MemberRepository;
import com.example.algostudy.repository.MessageRepository;
import com.example.algostudy.repository.Team.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvitationService {


    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final InviteTeamMemberRepository inviteTeamMemberRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public void acceptInvitation(InvitationMessage message) {
        Member member = message.getMember();
        Team team = message.getTeam();
        inviteTeamMemberRepository.delete(member.getInviteTeamMemberList().get(0));
        messageRepository.delete(message);

        member.setTeam(team);
        member.getInviteTeamMemberList().remove(0);
        member.getMessageQueue().remove(0);

        team.getMemberList().add(member);
        team.getInviteTeamMemberList().remove(0);

    }

    @Transactional
    public void doInvitation(String acceptYn, InvitationMessage message) {
        refresh(message);
        if (acceptYn.equals("y")) {
            acceptInvitation(message);
        } else {
            denyInvitation(message);
        }
    }

    @Transactional
    void refresh(InvitationMessage message) {
        message.setTeam((Team) Hibernate.unproxy(message.getTeam()));;
        message.setMember((Member) Hibernate.unproxy(message.getMember()));
    }

    private void denyInvitation(InvitationMessage message) {
        Member member = message.getMember();
        Team team = message.getTeam();
        inviteTeamMemberRepository.delete(member.getInviteTeamMemberList().get(0));
        messageRepository.delete(message);

        member.getInviteTeamMemberList().remove(0);
        member.getMessageQueue().remove(0);

        team.getInviteTeamMemberList().remove(0);
    }
}
