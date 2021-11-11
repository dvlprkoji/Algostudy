package com.example.algostudy.service;

import com.example.algostudy.domain.dto.CalendarDate;
import com.example.algostudy.domain.dto.CalendarMember;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.MissionCalendar;
import com.example.algostudy.domain.entity.Team;
import com.example.algostudy.repository.MissionCalendarRepository.MissionCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionCalendarService {

    private final MissionCalendarRepository missionCalendarRepository;


    public void save(MissionCalendar missionCalendar) {
        missionCalendarRepository.save(missionCalendar);
    }
    
    @Transactional
    public void toCalendarDto(ArrayList<CalendarDate> calendarDateList, ArrayList<CalendarMember> calendarMemberList, Member member) {
        Team team = member.getTeam();
        List<Member> memberList = team.getMemberList();
        Integer dateId = 1;
        for (int i=0; i< memberList.size(); i++) {
            calendarMemberList.add(CalendarMember.builder().id(Integer.toString(i+1)).label(memberList.get(i).getUsername()).build());
            List<MissionCalendar> missionCalendarList = memberList.get(i).getMissionCalendarList();
            for (int j = 0; j < missionCalendarList.size(); j++) {
                calendarDateList.add(CalendarDate.builder()
                                                    .id(Integer.toString(dateId++))
                                                    .label(missionCalendarList.get(j).getSuccessYn().equals("y") ? "O" : "X")
                                                    .date(missionCalendarList.get(j).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                                    .rowId(Integer.toString(i+1))
                                                    .build());
            }
        }
    }
}
