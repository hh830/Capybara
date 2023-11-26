package com.codingrecipe.member.dto.appointmentsDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppointmentsDTO {
    private LocalDate date; // 날짜
    private LocalTime time; // 시간
    private String hospitalId; // 병원 ID

    private String userId;

    public AppointmentsDTO(LocalDate date, LocalTime time, String hospitalId) {
        this.date = date;
        this.time = time;
        this.hospitalId = hospitalId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
