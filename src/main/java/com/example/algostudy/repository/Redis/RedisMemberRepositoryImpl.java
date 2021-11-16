package com.example.algostudy.repository.Redis;

import com.example.algostudy.domain.dto.Redis.RedisMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;

@RequiredArgsConstructor
public class RedisMemberRepositoryImpl implements RedisMemberRepositoryCustom {

    private final RedisTemplate redisTemplate;

    @Override
    public void saveOrUpdateIfExist(List<RedisMemberDto> allMembers, List<RedisMemberDto> successMembers) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        for (RedisMemberDto m : allMembers) {
            zSetOperations.addIfAbsent("member", m.getId(), (double)m.getClearMissionCnt());
        }
        for (RedisMemberDto m : successMembers) {
            Double exScore = zSetOperations.score("member", m.getId());
            zSetOperations.incrementScore("member", m.getId(), m.getClearMissionCnt()-exScore);
        }
    }

    @Override
    public Integer getRevRank(String key, String value) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.reverseRank(key, Long.parseLong(value)).intValue();
    }
}
