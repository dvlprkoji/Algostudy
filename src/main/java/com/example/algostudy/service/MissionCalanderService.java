package com.example.algostudy.service;

import com.example.algostudy.domain.entity.MissionCalander;
import com.example.algostudy.repository.MissionCalanderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionCalanderService {

    private final MissionCalanderRepository missionCalanderRepository;


    public void save(MissionCalander missionCalander) {
        missionCalanderRepository.save(missionCalander);
    }
}
