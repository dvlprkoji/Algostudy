package com.example.algostudy.service;

import com.example.algostudy.domain.entity.Mission;
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
}
