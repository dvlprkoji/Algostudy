package com.example.algostudy.service;

import com.example.algostudy.domain.dto.MemberLoginDto;
import com.example.algostudy.domain.dto.MemberRegisterForm;
import com.example.algostudy.domain.entity.*;
import com.example.algostudy.mapper.MemberMapper;
import com.example.algostudy.repository.Member.MemberRepository;
import com.example.algostudy.repository.MemberRoleRepository;
import com.example.algostudy.repository.MessageRepository;
import com.example.algostudy.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;
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

    public List<Member> findMemberByUsername(String memberNm) {
        return memberRepository.findByUsernameStartsWith(memberNm);
    }

    public Member findMemberById(long memberId) {
        return memberRepository.findById(memberId).get();
    }

    public void refresh(Member member) {
        member.getTeam();
        member.getInviteTeamMemberList();
    }

    public Message findMessageById(Long id){
        return messageRepository.findById(id).get();
    }

    public Member fetchMissionCalanderList(Member member) {
        return memberRepository.fetchWithMissionCalanderList(member.getId());
    }
}
