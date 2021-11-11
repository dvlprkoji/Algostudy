package com.example.algostudy.repository.Team;

import com.example.algostudy.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TeamRepository extends JpaRepository<Team, Long>, TeamRepositoryCustom {
    long countByStudyUrl(String studyUrl);
}
