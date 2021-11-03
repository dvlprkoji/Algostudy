package com.example.algostudy.repository.Member;

import com.example.algostudy.domain.dto.MemberRegisterForm;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.MissionCalander;

import java.util.List;

public interface MemberRepositoryCustom {
    boolean isDuplicate(MemberRegisterForm form);

    Member fetchWithMissionCalanderList(Long id);
}
