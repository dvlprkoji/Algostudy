package com.example.algostudy.repository;

import com.example.algostudy.domain.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {


    Mission findByMissionName(String missionName);
}
