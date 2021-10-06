package com.example.algostudy.repository.Member;

import com.example.algostudy.domain.dto.MemberRegisterForm;
import com.example.algostudy.domain.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.example.algostudy.domain.entity.QMember.member;

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
                .fetch().size() == 0 ? false : true;
    }
}
