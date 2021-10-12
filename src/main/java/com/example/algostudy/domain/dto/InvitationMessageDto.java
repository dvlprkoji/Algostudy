package com.example.algostudy.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;

@Getter
@Setter
public class InvitationMessageDto {
    private Long id;
    private String valid;
}
