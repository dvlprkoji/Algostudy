package com.example.algostudy.repository.Member;

import com.example.algostudy.domain.dto.MemberRegisterForm;
import com.example.algostudy.domain.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.List;

import static com.example.algostudy.domain.entity.QMember.member;
import static com.example.algostudy.domain.entity.QMissionCalendar.missionCalendar;
import static com.example.algostudy.domain.entity.QTeam.team;


public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private JPAQueryFactory qf;

    public MemberRepositoryImpl(EntityManager em) {
        qf = new JPAQueryFactory(em);
    }


    @Override
    public boolean isDuplicate(MemberRegisterForm form) {
        return qf.selectFrom(member)
                .where(member.username.eq(form.getUsername()).or(
                        member.bojId.eq(form.getBojId())).or(
                        member.email.eq(form.getEmail())))
                .fetch()
                .size() == 0 ? false : true;
    }

    @Override
    public Member fetchWithMissionCalendarList(Long id) {
        return qf.selectFrom(member)
                .leftJoin(member.missionCalendarList, missionCalendar)
                .fetchJoin()
                .where(member.id.eq(id))
                .fetchOne();
    }


    @Override
    public List<Member> findByTeam(Team t) {
        return qf.selectFrom(member)
                .join(member.team, team).fetchJoin()
                .fetch();




    }


}
