package com.example.algostudy.service;

import com.example.algostudy.controller.MissionController;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.Mission;
import com.example.algostudy.domain.entity.Team;
import com.example.algostudy.domain.entity.TeamMission;
import com.example.algostudy.repository.Member.MemberRepository;
import com.example.algostudy.repository.MissionRepository;
import org.aspectj.lang.annotation.RequiredTypes;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CrawlingServiceTest {

    @Autowired
    private CrawlingService crawlingService;
    @Autowired
    private MissionController missionController;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MissionRepository missionRepository;

    @Test
    @Rollback
    public void crawlerTest() throws IOException, ParseException {

        Member member1 = memberRepository.findByUsername("member1");

        Document crawler = crawlingService.crawler(member1);
        Mission bojMission = missionRepository.findByMissionName("1일 1백준 챌린지");

        missionController.checkMission(Arrays.asList(bojMission), member1);
    }

}