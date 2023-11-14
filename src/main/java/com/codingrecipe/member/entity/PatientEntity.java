package com.codingrecipe.member.entity;

import com.codingrecipe.member.dto.PatientDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "ex_patients")
public class PatientEntity {

    @Id //기본키
    //@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private String patient_id;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private LocalDate birth_date;

    @Column(unique = true) //제약조건
    private String phone_number;

    public static PatientEntity toPatientEntity(PatientDTO patientDTO){
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setPatient_id(patientDTO.getUserId()); //dto에 담긴걸 entity로 넘기기
        patientEntity.setName(patientDTO.getUserName());
        patientEntity.setPassword(patientDTO.getPassword());
        patientEntity.setBirth_date(patientDTO.getBirthDate());
        patientEntity.setPhone_number(patientDTO.getPhoneNumber());
        return patientEntity;
    }

}
