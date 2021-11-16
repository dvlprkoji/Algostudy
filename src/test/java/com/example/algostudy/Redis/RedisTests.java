package com.example.algostudy.Redis;

import com.example.algostudy.domain.dto.Redis.RedisMemberDto;
import com.example.algostudy.repository.Redis.RedisMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Optional;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisMemberRepository redisMemberRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {

        RedisMemberDto save = redisMemberRepository.save(new RedisMemberDto(1L, 10L));
        Optional<RedisMemberDto> byId = redisMemberRepository.findById(1L);

        Assertions.assertThat(save.equals((RedisMemberDto) byId.get()));
    }

}
