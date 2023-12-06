package com.codingrecipe.member.dto.hospitalDTO;

import com.codingrecipe.member.dto.doctorDTO.DoctorDTO;
import com.codingrecipe.member.entity.Hospital;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HospitalDetailsDTO {

    private String hospitalId;
    private String hospitalName;
    private String address;
    private String department;
    private String openDate;
    private String phoneNumber;
    private String operatingHours;
    private String breakTime;
    private Long likesCount;
    private boolean isLikedByUser;
    private List<DoctorDTO> doctors;
    private String hospitalStatus;

    public HospitalDetailsDTO(Hospital hospital, String operatingHours, String breakTime, Long likesCount, boolean isLikedByUser, String hospitalStatus, List<DoctorDTO> doctors) {
        this.hospitalId = hospital.getBusinessId();
        this.hospitalName = hospital.getName();
        this.address = hospital.getAddress();
        this.department = hospital.getDepartment();
        this.openDate = hospital.getOpenDate();
        this.phoneNumber = hospital.getPhoneNumber();
        this.operatingHours = operatingHours;
        this.breakTime = breakTime;
        this.likesCount = likesCount;
        this.isLikedByUser = isLikedByUser;
        this.hospitalStatus = hospitalStatus;
        this.doctors = doctors;
    }

/*
    public String getStatus() {
        return hospitalStatus;
    }*/
}

