package com.example.algostudy.service;

import com.example.algostudy.domain.dto.MemberLoginDto;
import com.example.algostudy.domain.dto.MemberRegisterForm;
import com.example.algostudy.domain.entity.*;
import com.example.algostudy.mapper.MemberMapper;
import com.example.algostudy.repository.Member.MemberRepository;
import com.example.algostudy.repository.MessageRepository;
import com.example.algostudy.repository.MissionCalendarRepository.MissionCalendarRepository;
import com.example.algostudy.repository.Team.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;
    private final MissionCalendarRepository missionCalendarRepository;

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

    public Member fetchMissionCalendarList(Member member) {
        return memberRepository.fetchWithMissionCalendarList(member.getId());
    }

    public void fetchAll(Member member) {

        Team team = teamRepository.findById(member.getTeam().getId()).get();
        member.setTeam(teamRepository.fetchWithMemberList(team));
        List<Member> fetchMemberList = memberRepository.findAllById(team.getMemberList().stream().map(m -> m.getId()).collect(Collectors.toList()))
                        .stream().map(proxy -> (Member)Hibernate.unproxy(proxy)).collect(Collectors.toList());
        for (Member m : fetchMemberList) {

        }



        team.setMemberList(fetchMemberList);

    }
}
