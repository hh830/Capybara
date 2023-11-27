package com.codingrecipe.member.dto.appointmentsDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class CheckAppointmentsDTO {
    private LocalDate date; // 날짜
    private LocalTime time; // 시간
    private String status; //예약 상태

    private String hospitalId; // 병원 ID
    private String hospitalName; //병원 이름

    private String userId; //환자아이디
    private String userName; //환자이름


}
