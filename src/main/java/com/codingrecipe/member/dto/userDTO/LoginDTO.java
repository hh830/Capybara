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
public class LoginDTO {

    private String userId;
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    public LoginDTO(Patients patients) {
        this.userId = patients.getPatientId();
        this.password = patients.getPassword();

    }
}
