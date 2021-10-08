package com.example.algostudy.service;

import com.example.algostudy.domain.dto.MemberLoginDto;
import com.example.algostudy.domain.dto.MemberRegisterForm;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.MemberRole;
import com.example.algostudy.domain.entity.Role;
import com.example.algostudy.mapper.MemberMapper;
import com.example.algostudy.repository.Member.MemberRepository;
import com.example.algostudy.repository.MemberRoleRepository;
import com.example.algostudy.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberLoginDto toMemberLoginDto(Member member) {
        return memberMapper.memberToLoginDto(member);
    }

    public boolean checkBeforeRegister(MemberRegisterForm memberRegisterForm, Model model) {
        if (memberRepository.isDuplicate(memberRegisterForm)) {
            model.addAttribute("duplicateError", "동일한 정보의 회원이 존재합니다");
            return false;
        }
        return true;
    }

    public void register(MemberRegisterForm form) {
        String encodedPassword = encodePassword(form.getPassword());
        Member member = Member.builder()
                .username(form.getUsername())
                .email(form.getEmail())
                .password(encodedPassword)
                .bojId(form.getBojId())
                .build();
        memberRepository.save(member);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
