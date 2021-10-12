package com.example.algostudy.mapper;

import com.example.algostudy.domain.dto.InvitationMessageDto;
import com.example.algostudy.domain.dto.MemberLoginDto;
import com.example.algostudy.domain.dto.MemberSearchDto;
import com.example.algostudy.domain.entity.InvitationMessage;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.MemberRole;
import com.example.algostudy.domain.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface InvitationMessageMapper{

    InvitationMessageMapper INSTANCE = Mappers.getMapper(InvitationMessageMapper.class);

    InvitationMessageDto toDto(InvitationMessage m);

}
