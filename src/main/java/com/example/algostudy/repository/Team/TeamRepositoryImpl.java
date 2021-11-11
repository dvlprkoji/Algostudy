package com.example.algostudy.repository.Team;

import com.example.algostudy.domain.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.List;

import static com.example.algostudy.domain.entity.QMember.member;
import static com.example.algostudy.domain.entity.QMissionCalendar.missionCalendar;
import static com.example.algostudy.domain.entity.QTeam.team;


public class TeamRepositoryImpl implements TeamRepositoryCustom {

    private JPAQueryFactory qf;

    public TeamRepositoryImpl(EntityManager entityManager) {
        qf = new JPAQueryFactory(entityManager);
    }

    @Override
    public Team fetchWithMemberList(Team t) {
        List<Member> memberList = qf.selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.team.id.eq(t.getId()))
                .fetch();

        for (Member m : memberList) {
            List<MissionCalendar> missionCalendarList = qf.selectFrom(missionCalendar)
                    .join(missionCalendar.member, member)
                    .where(member.id.eq(m.getId()))
                    .fetch();
            m.setMissionCalendarList(missionCalendarList);
        }

        t.setMemberList(memberList);

        return t;
    }
}
