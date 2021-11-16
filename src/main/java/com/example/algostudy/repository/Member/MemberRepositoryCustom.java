package com.example.algostudy.repository.Member;

import com.example.algostudy.domain.dto.MemberRegisterForm;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.MissionCalendar;
import com.example.algostudy.domain.entity.Team;

import java.util.List;
import java.util.Map;

public interface MemberRepositoryCustom {
    boolean isDuplicate(MemberRegisterForm form);

    Member fetchWithMissionCalendarList(Long id);


    List<Member> findByTeam(Team team);

    void bulkMissionCntUpdate(Map<Long, Integer> updateMemberList);
}
