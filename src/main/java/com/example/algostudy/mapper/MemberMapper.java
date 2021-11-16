package com.example.algostudy.mapper;

import com.example.algostudy.domain.dto.MemberLoginDto;
import com.example.algostudy.domain.dto.MemberSearchDto;
import com.example.algostudy.domain.dto.Redis.RedisMemberDto;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.MemberRole;
import com.example.algostudy.domain.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface MemberMapper extends GenericMapper<MemberLoginDto, Member>{
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(source = "memberRoleList", target = "roleList")
    MemberLoginDto memberToLoginDto(Member member);

    RedisMemberDto memberToRedisMemberDto(Member member);

//    @Transactional
    public default List<Role> memberRoleListToRoleList(List<MemberRole> memberRoleList) {
        return memberRoleList.stream().map(MemberRole::getRole).collect(Collectors.toList());
    }

    MemberSearchDto memberToMemberLoginDto(Member findMember);
}
