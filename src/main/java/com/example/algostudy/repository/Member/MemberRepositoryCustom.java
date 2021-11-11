package com.example.algostudy.repository.Member;

import com.example.algostudy.domain.dto.MemberRegisterForm;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.MissionCalendar;
import com.example.algostudy.domain.entity.Team;

import java.util.List;

public interface MemberRepositoryCustom {
    boolean isDuplicate(MemberRegisterForm form);

    Member fetchWithMissionCalendarList(Long id);


    List<Member> findByTeam(Team team);
}
