package com.example.algostudy.batch.chunk;

import com.example.algostudy.domain.dto.Redis.RedisMemberDto;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.Mission;
import com.example.algostudy.domain.entity.MissionCalendar;
import com.example.algostudy.domain.entity.Team;
import com.example.algostudy.mapper.MemberMapper;
import com.example.algostudy.repository.Member.MemberRepository;
import com.example.algostudy.service.CrawlingService;
import com.example.algostudy.service.MissionCalendarService;
import com.example.algostudy.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.jsoup.nodes.Document;
import org.mapstruct.factory.Mappers;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CustomItemProcessor implements ItemProcessor<Team, Team> {

    private final CrawlingService crawlingService;
    private final MissionCalendarService missionCalendarService;
    private final MemberRepository memberRepository;
    private final RedisService redisService;
    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);


    @Override
    public Team process(Team team) throws Exception {
        List<Member> memberList = team.getMemberList();
        List<Mission> missionList = team.getMissionList();
        List<Member> successMemberList = new ArrayList<>();
        Map<Member, Integer> successMemberMap = new HashMap<>();
        for (Member member : memberList) {
            boolean success = true;
            member = (Member) Hibernate.unproxy(member);
            // boj mission
            for (Mission mission : missionList) {
                if (mission.getId()==18) {
                    Document html = crawlingService.crawler(member);
                    if (!crawlingService.checkRecent(html)) {
                        success = false;
                    }
                }
            }
            MissionCalendar missionCalendar;
            if (success) {
                missionCalendar = MissionCalendar.builder()
                        .date(LocalDate.now().minusDays(1))
                        .successYn("y")
                        .member(member)
                        .build();
                member.setClearMissionCnt(member.getClearMissionCnt() + 1);
                successMemberList.add(member);
            } else {
                missionCalendar = MissionCalendar.builder()
                        .date(LocalDate.now().minusDays(1))
                        .successYn("n")
                        .member(member)
                        .build();
            }
            missionCalendarService.save(missionCalendar);

            member = memberRepository.fetchWithMissionCalendarList(member.getId());
            member.getMissionCalendarList().add(missionCalendar);
        }
        List<Member> allMemberList = memberRepository.findAll();

        List<RedisMemberDto> allMembers = memberListToRedisMemberList(allMemberList);
        List<RedisMemberDto> successMembers = memberListToRedisMemberList(successMemberList);

        redisService.updateOrSaveIfExist(allMembers, successMembers);
        return team;
    }

    private List<RedisMemberDto> memberListToRedisMemberList(List<Member> allMembers) {
        return allMembers.stream().map(m -> memberMapper.memberToRedisMemberDto(m)).collect(Collectors.toList());
    }

}
