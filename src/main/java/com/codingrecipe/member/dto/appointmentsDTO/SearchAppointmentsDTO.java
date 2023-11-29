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
public class SearchAppointmentsDTO {
    private LocalDate date; // 날짜
    private LocalTime time; // 시간
    private String status; //예약 상태

    private String hospitalId; // 병원 ID
    private String hospitalName; //병원 이름

    private String userId; //환자아이디
    private String userName; //환자이름

    public SearchAppointmentsDTO(LocalDate date, LocalTime time, String status, String hospitalId, String hospitalName, String userName) {
        this.date = date;
        this.time = time;
        this.status = status;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.userName = userName;
    }
}
