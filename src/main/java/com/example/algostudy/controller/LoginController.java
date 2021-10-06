package com.example.algostudy.controller;

import com.example.algostudy.domain.dto.MemberLoginForm;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {

    private static final String BadCredentialException = "이메일 혹은 비밀번호를 잘못 입력하셨거나 등록되지 않은 이메일입니다.";
    private static final String DefaultException = "오류가 발생하였습니다";


    @GetMapping("/login")
    public String login(HttpServletRequest request,
                        Model model,
                        @Nullable @RequestParam("error") String error) {
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("exception", BadCredentialException);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/";
    }


    @PostMapping("/login_proc")
    public String login(@Valid @ModelAttribute("form") MemberLoginForm form) {
        System.out.println("login");
        return "redirect:/";
    }
}
