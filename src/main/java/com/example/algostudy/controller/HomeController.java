package com.example.algostudy.controller;

import com.example.algostudy.common.CommonTools;
import com.example.algostudy.domain.dto.MemberLoginDto;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.Team;
import com.example.algostudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MemberService memberService;
    private final CommonTools commonTools;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal Member member, Model model) {
        if (member != null) {
            member = commonTools.refresh(member);
            MemberLoginDto memberLoginDto = memberService.toMemberLoginDto(member);
            model.addAttribute("member", memberLoginDto);
            Team team = member.getTeam();
            model.addAttribute("team", team);
            return "dashboard";
        }
        return "index";
    }
}
