package com.example.algostudy.domain.dto;

import com.example.algostudy.domain.entity.MemberRole;
import com.example.algostudy.domain.entity.Team;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class MemberSearchDto {
    private Long id;
    private String username;
}
