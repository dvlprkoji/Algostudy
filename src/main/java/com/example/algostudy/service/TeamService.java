package com.example.algostudy.service;

import com.example.algostudy.domain.dto.MissionForm;
import com.example.algostudy.domain.dto.TeamRegisterForm;
import com.example.algostudy.domain.entity.*;
import com.example.algostudy.repository.Hashtag.HashtagRepository;
import com.example.algostudy.repository.Image.ImageRepository;
import com.example.algostudy.repository.Member.MemberRepository;
import com.example.algostudy.repository.MissionRepository;
import com.example.algostudy.repository.Team.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.RegEx;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private final HashtagRepository hashtagRepository;
    private final MissionRepository missionRepository;



    public boolean checkUrl(TeamRegisterForm teamRegisterForm, BindingResult teamBindingResult) {
        String studyUrl = teamRegisterForm.getStudyUrl();
        if (!isUrlFormat(studyUrl)) {
            teamBindingResult.addError(new FieldError("teamRegisterForm", "studyUrl", "1~15 자리의 영소문자만 입력 가능합니다"));
            return false;
        }
        if (teamRepository.countByStudyUrl(studyUrl) != 0l) {
            teamBindingResult.addError(new FieldError("teamRegisterForm", "studyUrl", "동일한 주소의 스터디 URL이 존재합니다"));
            return false;
        }
        return true;
    }

    private boolean isUrlFormat(String studyUrl) {
        String EnglishOnlyRegEx = "^[a-z]{1,15}$";
        return studyUrl.matches(EnglishOnlyRegEx);
    }

    public void createTeam(Member member, TeamRegisterForm teamRegisterForm, MissionForm missionForm, Image image) {

        List<Member> memberList = new ArrayList<>();
        memberList.add(member);


        Team newTeam = Team.builder()
                        .mainImage(image)
                        .teamName(teamRegisterForm.getTeamName())
                        .teamDesc(teamRegisterForm.getTeamDesc())
                        .startDateTime(
                                LocalDateTime.of(
                                        LocalDate.parse(teamRegisterForm.getStartDate()),
                                        LocalTime.of(0,0,0,0)))
                        .endDateTime(
                                LocalDateTime.of(
                                        LocalDate.parse(teamRegisterForm.getEndDate()),
                                        LocalTime.of(23,59,59,59)))
                        .mainImage(image)
                        .memberList(memberList)
                        .status("beforeStart")
                        .studyUrl("https://algostudy.com/"+teamRegisterForm.getStudyUrl())
                        .build();

        List<Hashtag> hashtagList = Arrays.stream(teamRegisterForm.getHashtagList().split(","))
                                            .map(hashtagRepository::findByNameOrSave).collect(Collectors.toList());
        hashtagList.forEach(tag -> tag.setTeam(newTeam));
        newTeam.setHashtagList(hashtagList);

        member.setTeam(newTeam);

        List<Mission> missionList = missionRepository.findAllById(Arrays.stream(missionForm
                                                                                .getCheckedMissionList()
                                                                                .split(","))
                                                                        .map(Long::parseLong)
                                                                        .collect(Collectors.toList()));
        newTeam.setMissionList(missionList);
        teamRepository.save(newTeam);
    }
}
