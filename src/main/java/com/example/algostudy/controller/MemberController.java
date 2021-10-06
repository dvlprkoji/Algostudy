package com.example.algostudy.controller;

import com.example.algostudy.domain.dto.MemberLoginForm;
import com.example.algostudy.domain.dto.MemberRegisterForm;
import com.example.algostudy.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final RegisterService registerService;

//    @GetMapping("/login")
//    public String login(Model model) {
//        model.addAttribute("form", new MemberLoginForm());
//        return "login";
//    }

    @PostMapping("/login_proc")
    public String login(@Valid @ModelAttribute("form") MemberLoginForm form) {
        System.out.println("login");
        return "redirect:/";
    }

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
        if (registerService.checkBeforeRegister(form, model)) {
            registerService.register(form);
            model.addAttribute("registerSuccessMessage", "회원가입이 완료되었습니다");
            return "login";
        }
        return "register";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage";
    }
}
