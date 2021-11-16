package com.example.algostudy.service;

import com.example.algostudy.domain.dto.Redis.RedisMemberDto;
import com.example.algostudy.repository.Redis.RedisMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisMemberRepository redisMemberRepository;

    public void saveAll(List<RedisMemberDto> redisMemberDtoList) {
        redisMemberRepository.saveAll(redisMemberDtoList);
    }

    public void updateOrSaveIfExist(List<RedisMemberDto> allMembers, List<RedisMemberDto> redisMemberDtoList) {
        redisMemberRepository.saveOrUpdateIfExist(allMembers, redisMemberDtoList);
    }

    public Integer getRevRank(String key, String value){
        return redisMemberRepository.getRevRank(key, value);
    }
}
