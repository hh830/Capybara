package com.codingrecipe.member.dto;

import com.codingrecipe.member.entity.PatientEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PatientDTO {
    //private Long id;
    private String userId;
    @Size(min = 8, message = "비밀번호는 최수 8자 이상이어야 합니다.")
    private String password;
    private String userName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String phoneNumber;

    public static PatientDTO toPatientDTO(PatientEntity patientEntity){
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setUserId(patientEntity.getPatientId());
        patientDTO.setPassword(patientEntity.getPassword());
        patientDTO.setUserName(patientEntity.getName());
        patientDTO.setBirthDate(patientEntity.getBirthDate());
        patientDTO.setPhoneNumber(patientEntity.getPhoneNumber());
        return patientDTO;
    }
}
