package com.example.algostudy.security.authentication.service;

import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.MemberRole;
import com.example.algostudy.domain.entity.Role;
import com.example.algostudy.repository.Member.MemberRepository;
import com.example.algostudy.security.authentication.domain.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            if (memberRepository.countByEmail(email) == 0) {
                throw new UsernameNotFoundException("No user found with email: " + email);
            }
        }

        //Rolename 구하기
        List<String> memberRoleNameList = member.getMemberRoleList()
                .stream()
                .map(MemberRole::getRole)
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        return new UserDetail(member, memberRoleNameList);
    }

}
