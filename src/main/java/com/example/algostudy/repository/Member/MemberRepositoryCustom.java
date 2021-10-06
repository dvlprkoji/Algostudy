package com.example.algostudy.repository.Member;

import com.example.algostudy.domain.dto.MemberRegisterForm;

public interface MemberRepositoryCustom {
    boolean isDuplicate(MemberRegisterForm form);
}
