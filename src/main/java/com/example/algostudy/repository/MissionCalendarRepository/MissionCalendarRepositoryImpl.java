package com.example.algostudy.repository.MissionCalendarRepository;

import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.QMember;
import com.example.algostudy.domain.entity.QMissionCalendar;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.algostudy.domain.entity.QMissionCalendar.missionCalendar;

public class MissionCalendarRepositoryImpl implements MissionCalendarRepositoryCustom {

    private JPAQueryFactory qf;

    public MissionCalendarRepositoryImpl(EntityManager entityManager) {
        this.qf = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Member> findByMemberId(Long id) {
        return null;
    }
}
