package com.example.algostudy.repository.Member;

import com.example.algostudy.domain.dto.MemberRegisterForm;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.MissionCalander;
import com.example.algostudy.domain.entity.QMember;
import com.example.algostudy.domain.entity.QMissionCalander;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.List;

import static com.example.algostudy.domain.entity.QMember.member;
import static com.example.algostudy.domain.entity.QMissionCalander.missionCalander;

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
    public Member fetchWithMissionCalanderList(Long id) {
        return qf.selectFrom(member)
                .leftJoin(member.missionCalanderList, missionCalander)
                .fetchJoin()
                .where(member.id.eq(id))
                .fetchOne();
    }
}
