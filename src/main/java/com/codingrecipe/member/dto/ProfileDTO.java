package com.codingrecipe.member.dto;

import com.codingrecipe.member.entity.Patients;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProfileDTO {
    private String password;
    private String userName;
    private String phoneNumber;

    public ProfileDTO (Patients patients){
        this.password = patients.getPassword();
        this.userName = patients.getName();
        this.phoneNumber = patients.getPhoneNumber();
    }
}
