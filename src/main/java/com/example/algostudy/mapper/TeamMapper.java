package com.example.algostudy.mapper;

import com.example.algostudy.domain.dto.TeamDto;
import com.example.algostudy.domain.entity.InviteTeamMember;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface TeamMapper extends GenericMapper<TeamDto, Team>{
    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

    @Mapping(source = "inviteTeamMemberList", target="inviteMemberList")
    TeamDto teamToTeamDto(Team team);

    public default List<Member> inviteTeamMemberListToInviteMemberList(List<InviteTeamMember> inviteTeamMemberList){
        return inviteTeamMemberList.stream().map(InviteTeamMember::getMember).collect(Collectors.toList());
    }

}
