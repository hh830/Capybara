package com.codingrecipe.member.dto.medicalRecordDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecordDetailsDTO {

    private int recordId;

    private String content; //의사소견

    private LocalDate recordDate; //진료날짜

    private String patientName; //환자 이름

    private String birthDate; //환자 생일

    private String hospitalId; //병원 아이디

    private String hospitalName; //병원명

    private String phoneNumber; //병원번호

    private String licenseNumber; //의사면허번호

    private String doctorName;

    private boolean isLikes; //좋아요 누른 상태인지

    private long countLikes; //좋아요 총 수

    public RecordDetailsDTO(int recordId, String content, LocalDate recordDate, String patientName, String birthDate, String hospitalId, String hospitalName, String phoneNumber, String licenseNumber, String doctorName, boolean isLikes, long countLikes) {
        this.recordId = recordId;
        this.content = content;
        this.recordDate = recordDate;
        this.patientName = patientName;
        this.birthDate = birthDate;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
        this.doctorName = doctorName;
        this.isLikes = isLikes;
        this.countLikes = countLikes;
    }
}
