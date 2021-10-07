package com.example.algostudy.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MissionDto {
    private Long id;
    private String missionName;
    private String missionDesc;
    private String imagePath;
}
