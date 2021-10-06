package com.example.algostudy.controller;

import com.example.algostudy.domain.dto.MemberLoginDto;
import com.example.algostudy.domain.entity.Member;
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

    @GetMapping("/")
    public String home(@AuthenticationPrincipal Member member, Model model) {
        if (member != null) {
            MemberLoginDto memberLoginDto = memberService.toMemberLoginDto(member);
            model.addAttribute("member", memberLoginDto);
            return "dashboard";
        }

        return "index";
    }
}
