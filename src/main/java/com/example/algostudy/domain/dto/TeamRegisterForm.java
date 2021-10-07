package com.example.algostudy.domain.dto;

import com.example.algostudy.domain.entity.Hashtag;
import com.example.algostudy.domain.entity.Mission;
import lombok.Data;

import java.util.List;

@Data
public class TeamRegisterForm {

    private String teamName;
    private String teamDesc;
    private String imagePath;
    private String startDateTime;
    private String endDateTime;
    private String studyUrl;
    private List<Hashtag> hashtagList;
    private List<Mission> missionList;
}

