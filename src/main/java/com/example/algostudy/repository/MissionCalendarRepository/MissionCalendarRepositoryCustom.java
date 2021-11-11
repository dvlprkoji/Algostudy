package com.example.algostudy.repository.MissionCalendarRepository;

import com.example.algostudy.domain.entity.Member;

import java.util.List;

public interface MissionCalendarRepositoryCustom {
    List<Member> findByMemberId(Long id);
}
