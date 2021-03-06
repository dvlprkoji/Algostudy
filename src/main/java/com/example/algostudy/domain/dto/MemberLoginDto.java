package com.example.algostudy.domain.dto;

import com.example.algostudy.domain.entity.Message;
import com.example.algostudy.domain.entity.Role;
import com.example.algostudy.domain.entity.Team;
import lombok.Data;

import java.util.List;

@Data
public class MemberLoginDto {
    private Long id;
    private String username;
    private String email;
    private Team team;
    private List<Role> roleList;
    private List<Message> messageQueue;
}
