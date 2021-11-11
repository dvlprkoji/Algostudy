package com.example.algostudy.repository.MissionCalendarRepository;

import com.example.algostudy.domain.entity.MissionCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionCalendarRepository extends JpaRepository<MissionCalendar, Long>, MissionCalendarRepositoryCustom {
}
