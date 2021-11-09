package com.example.algostudy.controller;

import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.Mission;
import com.example.algostudy.domain.entity.MissionCalander;
import com.example.algostudy.service.CrawlingService;
import com.example.algostudy.service.MemberService;
import com.example.algostudy.service.MissionCalanderService;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MissionController {

    private final MemberService memberService;
    private final CrawlingService crawlingService;
    private final MissionCalanderService missionCalanderService;

    public void checkMission(List<Mission> missionList, Member member) throws IOException, ParseException {
        boolean success = true;
        // boj mission
        for (Mission mission : missionList) {
            if (mission.getId()==18) {
                Document html = crawlingService.crawler(member);
                if (!crawlingService.checkRecent(html)) {
                    success = false;
                }
            }
        }
        MissionCalander missionCalander;
        if (success) {
             missionCalander = MissionCalander.builder()
                    .date(LocalDate.now().minusDays(1))
                    .successYn("y")
                    .member(member)
                    .build();
        } else {
            missionCalander = MissionCalander.builder()
                    .date(LocalDate.now().minusDays(1))
                    .successYn("n")
                    .member(member)
                    .build();
        }
        missionCalanderService.save(missionCalander);
        member = memberService.fetchMissionCalanderList(member);
        member.getMissionCalanderList().add(missionCalander);
    }

}
