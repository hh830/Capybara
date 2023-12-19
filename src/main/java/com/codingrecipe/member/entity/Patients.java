package com.codingrecipe.member.entity;

import com.codingrecipe.member.dto.userDTO.RegistrationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor // Lombok을 사용하여 기본 생성자를 추가
@Table(name = "patients")
public class Patients {

    @Id //기본키
    @Column(name = "patient_id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private String patientId;

    @Column
    private String password;

    @Column
    private String name;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;


    @OneToMany(mappedBy = "patients")
    private Set<Appointments> appointments;

    @OneToMany(mappedBy = "patients")
    private Set<MedicalRecords> medicalRecords;


    public Patients(RegistrationDTO registrationDTO) {
        this.patientId = registrationDTO.getUserId();
        this.password = registrationDTO.getPassword();
        this.name = registrationDTO.getUserName();
        this.birthDate = registrationDTO.getBirthDate();
        this.phoneNumber = registrationDTO.getPhoneNumber();

    }

}
