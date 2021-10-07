package com.example.algostudy.mapper;

import com.example.algostudy.domain.dto.MissionDto;
import com.example.algostudy.domain.entity.Mission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
public interface MissionMapper extends GenericMapper<MissionDto, Mission> {

    MissionMapper INSTANCE = Mappers.getMapper(MissionMapper.class);
}
