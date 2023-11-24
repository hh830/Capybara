package com.codingrecipe.member.dto.userDTO;

import com.codingrecipe.member.entity.Patients;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegistrationDTO {
    //private Long id;
    private String userId;
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;
    private String userName;
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    //private LocalDate birthDate;
    private String birthDate; // LocalDate 대신 String으로 선언

    private String phoneNumber;


    public RegistrationDTO(Patients patients) {
        this.userId = patients.getPatientId();
        this.password = patients.getPassword();
        this.userName = patients.getName();
        this.birthDate = patients.getBirthDate();
        this.phoneNumber = patients.getPhoneNumber();
    }

}
