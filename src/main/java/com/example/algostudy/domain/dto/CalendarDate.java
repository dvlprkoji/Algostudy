package com.example.algostudy.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class CalendarDate {
    String id;
    String label;
    String rowId;
    String date;
}
