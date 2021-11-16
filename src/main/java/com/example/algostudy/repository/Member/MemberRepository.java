package com.example.algostudy.repository.Member;

import com.example.algostudy.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Member findByEmail(String username);

    int countByEmail(String username);

    Member findByUsername(String username);

    List<Member> findByUsernameStartsWith(String memberNm);

    @Modifying
    @Query("update Member m set m.clearMissionCnt = m.clearMissionCnt+1 where m.id in :memberList")
    int bulkMissionCntPlus(@Param("memberList") List<Long> memberList);
}
