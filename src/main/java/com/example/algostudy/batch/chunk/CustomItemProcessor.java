package com.example.algostudy.batch.chunk;

import com.example.algostudy.controller.MissionController;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.Mission;
import com.example.algostudy.domain.entity.MissionCalander;
import com.example.algostudy.domain.entity.Team;
import com.example.algostudy.repository.Member.MemberRepository;
import com.example.algostudy.service.CrawlingService;
import com.example.algostudy.service.MemberService;
import com.example.algostudy.service.MissionCalanderService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CustomItemProcessor implements ItemProcessor<Team, Team> {

    private final CrawlingService crawlingService;
    private final MissionCalanderService missionCalanderService;
    private final MemberRepository memberRepository;


    @Override
    public Team process(Team team) throws Exception {
        List<Member> memberList = team.getMemberList();
        List<Mission> missionList = team.getMissionList();
        for (Member member : memberList) {
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
            member = memberRepository.fetchWithMissionCalanderList(member.getId());
            member.getMissionCalanderList().add(missionCalander);
        }
        return team;
    }

}
