package com.example.algostudy.domain.dto.Redis;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("member")
@Getter
public class RedisMemberDto {
    Long id;
    Long clearMissionCnt;

    public RedisMemberDto(Long id, Long clearMissionCnt) {
        this.id = id;
        this.clearMissionCnt = clearMissionCnt;
    }
}
