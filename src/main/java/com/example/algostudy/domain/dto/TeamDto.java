package com.example.algostudy.domain.dto;

import com.example.algostudy.domain.entity.InviteTeamMember;
import com.example.algostudy.domain.entity.Member;
import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private List<Member> inviteMemberList;
    private List<Member> memberList;
    private Member adminMember;
}
