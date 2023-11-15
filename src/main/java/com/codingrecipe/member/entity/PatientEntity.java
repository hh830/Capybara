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
    @Column(name = "patient_id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private String patientId;

    @Column
    private String password;

    @Column
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(unique = true, name = "phone_number") //제약조건
    private String phoneNumber;

    public static PatientEntity toPatientEntity(PatientDTO patientDTO){
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setPatientId(patientDTO.getUserId()); //dto에 담긴걸 entity로 넘기기
        patientEntity.setName(patientDTO.getUserName());
        patientEntity.setPassword(patientDTO.getPassword());
        patientEntity.setBirthDate(patientDTO.getBirthDate());
        patientEntity.setPhoneNumber(patientDTO.getPhoneNumber());
        return patientEntity;
    }

}
