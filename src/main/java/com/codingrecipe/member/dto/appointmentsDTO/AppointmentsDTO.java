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
    private Long id; //예약 아이디
    private LocalDate date; // 날짜
    private LocalTime time; // 시간
    private String appointmentStatus; //예약 상태

    private String hospitalId; // 병원 ID
    private String hospitalName; //병원 이름
    private String hospitalAddress;

    private String userName; //환자이름

    public AppointmentsDTO(String businessId, String hospitalName, String hospitalAddress, Long id, LocalDate date, LocalTime time, String status, String userName) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.appointmentStatus = status;

        this.hospitalId = businessId;
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;

        this.userName = userName;

    }


}
