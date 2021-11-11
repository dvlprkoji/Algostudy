package com.example.algostudy.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
public class CalendarMember {
    String id;
    String label;
}
