package com.example.algostudy.controller;

import com.example.algostudy.common.CommonTools;
import com.example.algostudy.domain.dto.*;
import com.example.algostudy.domain.entity.*;
import com.example.algostudy.mapper.MemberMapper;
import com.example.algostudy.mapper.MissionMapper;
import com.example.algostudy.mapper.TeamMapper;
import com.example.algostudy.service.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class StudyController {

    private final MissionMapper missionMapper = Mappers.getMapper(MissionMapper.class);
    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);
    private final TeamMapper teamMapper = Mappers.getMapper(TeamMapper.class);
    private final MemberService memberService;
    private final MissionService missionService;
    private final ImageService imageService;
    private final CommonTools commonTools;
    private final TeamService teamService;
    private final InvitationService invitationService;
    private final MissionCalendarService missionCalendarService;


    @GetMapping("/study/new")
    public String addStudy(@AuthenticationPrincipal Member member, Model model) {
        model.addAttribute("member", memberMapper.toDto(member));
        List<Mission> missionList = missionService.findAll();
        List<MissionDto> missionDtoList = missionList.stream().map(missionMapper::toDto).collect(Collectors.toList());
        model.addAttribute("missionList", missionDtoList);
        model.addAttribute("missionForm", new MissionForm());
        model.addAttribute("teamRegisterForm", new TeamRegisterForm());
        return "study-new";
    }

    @PostMapping("/study/new")
    public String addStudy(@AuthenticationPrincipal Member member,
                           @RequestParam("mainImage") MultipartFile mainImage,
                           @Valid @ModelAttribute("teamRegisterForm") TeamRegisterForm teamRegisterForm,
                           BindingResult teamBindingResult,
                           @Valid @ModelAttribute("missionForm") MissionForm missionForm,
                           BindingResult missionBindingResult,
                           HttpServletRequest request, Model model) throws IOException {
        model.addAttribute("member", memberMapper.toDto(member));
        if (teamBindingResult.hasErrors() || missionBindingResult.hasErrors()) {
            List<Mission> missionList = missionService.findAll();
            List<MissionDto> missionDtoList = missionList.stream().map(missionMapper::toDto).collect(Collectors.toList());
            model.addAttribute("missionList", missionDtoList);
        }
        teamService.checkUrl(teamRegisterForm, teamBindingResult);

        if (teamBindingResult.hasErrors() || missionBindingResult.hasErrors()) {
            return "study-new";
        }

        Image saveImage;
        if (!mainImage.isEmpty()) {
            String path = imageService.upload(mainImage, "algostudy");
            saveImage = imageService.saveImage(mainImage, path);
        } else {
            saveImage = imageService.getDefaultTeamImage();
        }

        teamService.createTeam(member, teamRegisterForm, missionForm, saveImage);


        commonTools.printParams(request);
        System.out.println("teamRegisterForm = " + teamRegisterForm);
        System.out.println("missionForm = " + missionForm);
        System.out.println("teamBindingResult = " + teamBindingResult);
        System.out.println("missionBindingResult = " + missionBindingResult);
        System.out.println("mainImage = " + mainImage);
        member = commonTools.refresh(member);
        return "redirect:/";
    }


    @GetMapping("/study/search")
    public String searchStudy(@AuthenticationPrincipal Member member, Model model) {
        model.addAttribute("member", memberMapper.toDto(member));
        return "study-search";
    }

    @GetMapping("/study/mystudy")
    public String myStudy(@AuthenticationPrincipal Member member, Model model) {
        model.addAttribute("member", memberMapper.toDto(member));
        String status = member.getTeam().getStatus();
        if (status.equals("beforeStart")) {
            member.setTeam(teamService.refresh(member.getTeam()));
            member.getTeam().setAdminMember((Member)Hibernate.unproxy(member.getTeam().getAdminMember()));
            model.addAttribute("team", teamMapper.teamToTeamDto(member.getTeam()));
            return "recruit";
        } else if (status.equals("onProgress")) {
            ArrayList<CalendarMember> calendarMemberList = new ArrayList<>();
            ArrayList<CalendarDate> calendarDateList = new ArrayList<>();
            joinFetch(member);
            missionCalendarService.toCalendarDto(calendarDateList, calendarMemberList, member);
            model.addAttribute("calendarMemberList", calendarMemberList);
            model.addAttribute("calendarDateList", calendarDateList);
            return "mystudy";
        }
        return null;
    }

    private void joinFetch(Member member) {
        memberService.fetchAll(member);
    }

    @GetMapping("/study/start")
    public String startStudy(@AuthenticationPrincipal Member member) {
        teamService.start(member.getTeam());
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/study/invite/new")
    public void invite(@AuthenticationPrincipal Member member
            , @RequestParam(name = "memberId") String memberId) {

        Member findMember = memberService.findMemberById(Long.parseLong(memberId));
        teamService.sendInvitation(member.getTeam(), findMember);
    }

    @ResponseBody
    @GetMapping("/study/invite/cancel")
    public void invitationCancel(@AuthenticationPrincipal Member member
            ,@RequestParam(name = "memberId") String memberId) {
        Member findMember = memberService.findMemberById(Long.parseLong(memberId));
        invitationService.cancelInvitation(member.getTeam(), findMember);
    }

    @GetMapping("/study/invite/decision")
    public String invitationDecision(@AuthenticationPrincipal Member member
                                    ,@RequestParam(name = "id") String messageId
                                    ,@RequestParam(name = "accept") String accept) {
        InvitationMessage message = (InvitationMessage) memberService.findMessageById(Long.parseLong(messageId));
        invitationService.doInvitation(accept, message);
        return "redirect:/";

    }
}
