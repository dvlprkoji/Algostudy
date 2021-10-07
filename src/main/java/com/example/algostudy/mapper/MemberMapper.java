package com.example.algostudy.mapper;

import com.example.algostudy.domain.dto.MemberLoginDto;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.MemberRole;
import com.example.algostudy.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(source = "memberRoleList", target = "roleList")
    MemberLoginDto memberToLoginDto(Member member);

    public default List<Role> memberRoleListToRoleList(List<MemberRole> memberRoleList) {
        return memberRoleList.stream().map(MemberRole::getRole).collect(Collectors.toList());
    }
}
