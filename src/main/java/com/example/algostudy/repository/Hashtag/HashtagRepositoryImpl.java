package com.example.algostudy.repository.Hashtag;

import com.example.algostudy.domain.entity.Hashtag;
import com.example.algostudy.domain.entity.QHashtag;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.List;

import static com.example.algostudy.domain.entity.QHashtag.hashtag;

public class HashtagRepositoryImpl implements HashtagRepositoryCustom {

    private JPAQueryFactory qf;

    public HashtagRepositoryImpl(EntityManager em) {
        qf = new JPAQueryFactory(em);
    }

//    @Override
//    public List<Hashtag> findByNameOrSaveIfNotExist(String name) {
//        List<Hashtag> hashtagList = qf.selectFrom(hashtag)
//                .where(hashtag.name.eq(name))
//                .fetch();
//    }
}
