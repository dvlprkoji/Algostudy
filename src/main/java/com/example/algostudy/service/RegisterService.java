package com.example.algostudy.service;

import com.example.algostudy.domain.dto.MemberRegisterForm;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
