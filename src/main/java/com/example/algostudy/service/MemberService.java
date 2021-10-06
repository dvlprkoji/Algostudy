package com.example.algostudy.service;

import com.example.algostudy.domain.dto.MemberLoginDto;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.MemberRole;
import com.example.algostudy.domain.entity.Role;
import com.example.algostudy.mapper.MemberMapper;
import com.example.algostudy.repository.MemberRoleRepository;
import com.example.algostudy.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);

    public MemberLoginDto toMemberLoginDto(Member member) {
        return memberMapper.memberToLoginDto(member);
    }
}
