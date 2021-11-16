package com.example.algostudy.repository.Redis;

import com.example.algostudy.domain.dto.Redis.RedisMemberDto;
import org.springframework.data.repository.CrudRepository;

public interface RedisMemberRepository extends CrudRepository<RedisMemberDto, Long>, RedisMemberRepositoryCustom {
}
