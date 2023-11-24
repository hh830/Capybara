package com.codingrecipe.member.dto.hospitalDTO;

import com.codingrecipe.member.entity.Hospital;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HospitalDTO {

    private String name;
    private String address;
    private String department;
    private String operatingHours;
    private Long likeCount;
/*
    public HospitalDTO(Hospital hospital, String operatingHours, Long likeCount) {
        this.name = hospital.getName();
        this.address = hospital.getAddress();
        this.department = hospital.getDepartment();
        this.operatingHours = operatingHours;
        this.likeCount = likeCount;
    }*/

    public HospitalDTO(String name, String address, String department, String operatingHours, Long likeCount) {
        this.name = name;
        this.address = address;
        this.department = department;
        this.operatingHours = operatingHours;
        this.likeCount = likeCount;
    }

    // toString 메서드 (선택적)
    @Override
    public String toString() {
        return "HospitalDto{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", department='" + department + '\'' +
                ", operatingHours='" + operatingHours + '\'' +
                ", likeCount=" + likeCount +
                '}';
    }
}
