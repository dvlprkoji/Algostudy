package com.example.algostudy.Mapper;

import com.example.algostudy.domain.dto.MemberLoginDto;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.MemberRole;
import com.example.algostudy.domain.entity.Role;
import com.example.algostudy.mapper.MemberMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class MapperTest {

    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);


    @Test
    void memberRoleListToRoleListTest() {
        MemberRole memberRole = MemberRole.builder().build();

        Role adminRole = Role.builder()
                .id(0L)
                .roleDesc("admin")
                .roleName("admin")
                .memberRoleList(Arrays.asList(memberRole))
                .build();

        Member adminMember = Member.builder()
                .username("admin")
                .password("admin")
                .email("admin")
                .bojId("admin")
                .id(0L)
                .oAuth2Id("admin")
                .memberRoleList(Arrays.asList(memberRole))
                .build();

        memberRole.setMember(adminMember);
        memberRole.setRole(adminRole);


        MemberLoginDto memberLoginDto = memberMapper.memberToLoginDto(adminMember);
        System.out.println("memberLoginDto = " + memberLoginDto);

    }
}
