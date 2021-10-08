package com.example.algostudy.domain.dto;

import com.example.algostudy.domain.entity.Hashtag;
import com.example.algostudy.domain.entity.Mission;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TeamRegisterForm {

    @NotEmpty(message = "스터디 이름을 입력해주세요")
    private String teamName;
    @NotEmpty(message = "스터디 소개를 입력해주세요")
    private String teamDesc;
    @NotEmpty(message = "시작일을 입력해주세요")
    private String startDate;
    @NotEmpty(message = "종료일을 입력해주세요")
    private String endDate;
    @NotEmpty(message = "스터디 URL을 입력해주세요")
    private String studyUrl;
    @NotEmpty(message = "최소 1개의 해시태그를 설정해주세요")
    private String hashtagList;
}

