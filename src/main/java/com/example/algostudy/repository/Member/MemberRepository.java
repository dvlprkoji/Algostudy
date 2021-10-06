package com.example.algostudy.repository.Member;

import com.example.algostudy.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Member findByEmail(String username);

    int countByEmail(String username);
}
