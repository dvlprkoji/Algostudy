package com.example.algostudy.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MemberRegisterForm {
    @NotEmpty(message = "이름은 필수 항목입니다")
    private String username;
    @NotEmpty(message = "이메일은 필수 항목입니다")
    private String email;
    @NotEmpty(message = "비밀번호는 필수 항목입니다")
    private String password;
    @NotEmpty(message = "백준 ID는 필수 항목입니다")
    private String bojId;
}
