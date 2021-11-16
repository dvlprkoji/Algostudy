package com.example.algostudy.repository.Redis;

import com.example.algostudy.domain.dto.Redis.RedisMemberDto;

import java.util.List;

public interface RedisMemberRepositoryCustom {
    public void saveOrUpdateIfExist(List<RedisMemberDto> allMembers, List<RedisMemberDto> successMembers);

    Integer getRevRank(String key, String value);
}
