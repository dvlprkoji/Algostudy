package com.example.algostudy.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MissionForm {

    @NotEmpty(message = "최소 1개의 미션을 선택해 주세요")
    private String checkedMissionList;
}
