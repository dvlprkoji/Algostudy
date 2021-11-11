package com.example.algostudy.repository.Team;

import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.Team;

public interface TeamRepositoryCustom {
    Team fetchWithMemberList(Team team);
}
