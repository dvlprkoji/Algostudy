package com.example.algostudy.controller;

import com.example.algostudy.domain.dto.MemberRegisterForm;
import com.example.algostudy.domain.dto.MemberSearchDto;
import com.example.algostudy.domain.dto.TeamDto;
import com.example.algostudy.domain.entity.InvitationMessage;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.Message;
import com.example.algostudy.domain.entity.Team;
import com.example.algostudy.mapper.MemberMapper;
import com.example.algostudy.mapper.MissionMapper;
import com.example.algostudy.mapper.TeamMapper;
import com.example.algostudy.service.MemberService;
import com.example.algostudy.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);
    private final TeamMapper teamMapper = Mappers.getMapper(TeamMapper.class);
    private final TeamService teamService;


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("form", new MemberRegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") MemberRegisterForm form, BindingResult bindingResult, Model model) {
        System.out.println("MemberController.register");
        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult = " + bindingResult);
            return "register";
        }
        if (memberService.checkBeforeRegister(form, model)) {
            memberService.register(form);
            model.addAttribute("registerSuccessMessage", "회원가입이 완료되었습니다");
            return "login";
        }
        return "register";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage";
    }

    @ResponseBody
    @GetMapping("/search/member")
    public List<MemberSearchDto> searchMember(@RequestParam(name = "memberNm") String memberNm) {
        if (memberNm.isEmpty()) {
            return new ArrayList<>();
        }
        List<Member> memberList = memberService.findMemberByUsername(memberNm);
        return memberList.stream().map(memberMapper::memberToMemberLoginDto).collect(Collectors.toList());
    }

    @GetMapping("/message")
    public String myMessage(@AuthenticationPrincipal Member member
            , @RequestParam("id") String id
            , Model model) {
        model.addAttribute("member", memberMapper.toDto(member));
        for (Message message : member.getMessageQueue()) {
            if (message.getId() == Long.parseLong(id)) {
                if (message instanceof InvitationMessage) {
                    InvitationMessage invitationMessage = (InvitationMessage) message;
                    Team team = invitationMessage.getTeam();
                    team = teamService.refresh(team);
                    model.addAttribute("team", team);
                    model.addAttribute("messageId", message.getId());
                    return "invitation";
                }
            }
        }
        return "404";
    }
}
