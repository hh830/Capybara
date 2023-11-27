package com.codingrecipe.member.entity;

import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.hospitalRepository.HospitalRepository;
import com.codingrecipe.member.repository.userRepository.PatientRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@NoArgsConstructor // Lombok을 사용하여 기본 생성자를 추가
@Table(name = "appointments")
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private int appointmentId;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "appointment_time")
    private LocalTime appointmentTime;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patients patients;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Hospital hospital;

    @Version
    private Long version;

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

    public void setHospitalId(String hospitalId, HospitalRepository hospitalRepository) {
        Hospital hospitals = hospitalRepository.findById(hospitalId).orElse(null);
        if (hospitals != null) {
            this.hospital = hospitals;
        } else {
            // 병원이 존재하지 않을 때의 처리
            throw new CustomValidationException(HttpStatus.UNAUTHORIZED.value(), "존재하지 않는 병원");
        }
    }
}
