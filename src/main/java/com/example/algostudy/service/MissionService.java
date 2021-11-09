package com.example.algostudy.service;

import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.Mission;
import com.example.algostudy.domain.entity.Team;
import com.example.algostudy.domain.entity.TeamMission;
import com.example.algostudy.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

    public List<Mission> findAll() {
        return missionRepository.findAll();
    }

    public void checkMission(Mission mission, Member member) {
        // boj mission
        if (mission.getId().equals("18")) {

        }
    }
}
