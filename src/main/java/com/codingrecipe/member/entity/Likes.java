package com.codingrecipe.member.entity;

import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.hospitalRepository.HospitalRepository;
import com.codingrecipe.member.repository.PatientRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor // Lombok을 사용하여 기본 생성자를 추가
@Table(name = "likes")
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private int likeId;

    @ManyToOne
    @JoinColumn(name = "patient_id") // 외래키
    private Patients patients;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Hospital hospital;

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
