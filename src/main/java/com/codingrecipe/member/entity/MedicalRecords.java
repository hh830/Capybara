package com.codingrecipe.member.entity;

import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.doctorRepository.DoctorRepository;
import com.codingrecipe.member.repository.hospitalRepository.HospitalRepository;
import com.codingrecipe.member.repository.userRepository.PatientRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor // Lombok을 사용하여 기본 생성자를 추가
@Table(name = "medicalrecords")
public class MedicalRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private int recordId;

    @Column
    private String content;

    @Column(name = "record_date")
    private LocalDate recordDate;

    @ManyToOne
    @JoinColumn(name = "patient_id") // 외래키
    private Patients patients;

    @ManyToOne
    @JoinColumn(name = "license_number")
    private Doctors doctors;

    // 이 메소드는 Patients 엔티티를 찾아서 설정합니다.
    public void setUserId(String userId, PatientRepository patientRepository) {
        Patients patient = patientRepository.findByPatientId(userId);
        if (patient != null) {
            this.patients = patient;
        } else {
            // 사용자가 존재하지 않을 때의 처리
            throw new CustomValidationException(HttpStatus.UNAUTHORIZED.value(), "존재하지 않는 사용자");
        }
    }

    // 이 메소드는 Doctors 엔티티를 찾아서 설정합니다.
    public void setDoctorsId(String doctorsId, DoctorRepository doctorRepository) {
        Doctors doctors = doctorRepository.findByLicenseNumber(doctorsId);
        if (doctors != null) {
            this.doctors = doctors;
        } else {
            // 사용자가 존재하지 않을 때의 처리
            throw new CustomValidationException(HttpStatus.UNAUTHORIZED.value(), "존재하지 않는 의사");
        }
    }
}
