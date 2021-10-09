package com.example.algostudy.common;

import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.repository.Member.MemberRepository;
import com.example.algostudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommonTools {
    private final MemberRepository memberRepository;

    public void printParams(HttpServletRequest request) {
        System.out.println("=================["+request.getRequestURI()+"]=================");
        request.getParameterNames().asIterator().forEachRemaining(name -> {
            System.out.println(name + " = " + request.getParameter(name));
        });
        System.out.println("================================================================");


    }

    public Member refresh(Member member) {
        member = memberRepository.findById(member.getId()).get();
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                       member, member.getPassword(), SecurityContextHolder.getContext().getAuthentication().getAuthorities())
                );
        return member;
    }
}
