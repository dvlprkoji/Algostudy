package com.example.algostudy.security.authentication.domain;

import com.example.algostudy.domain.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDetail extends User {
    private Member member;
    private List<String> roleNameList;



    public UserDetail(Member member, List<String> roleNameList) {
        super(member.getEmail(), member.getPassword(), roleNameList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        this.member = member;
        this.roleNameList = roleNameList;
    }
}
