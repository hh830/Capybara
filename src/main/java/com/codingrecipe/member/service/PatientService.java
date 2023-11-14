package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.PatientDTO;
import com.codingrecipe.member.entity.PatientEntity;
import com.codingrecipe.member.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    public void save(PatientDTO patientDTO) {
        //repository의 save 메서드 호출 (조건: entity객체 넘겨주기)
        //1. dto -> entity 변환
        //2. repository의 save 메서드 호출

        PatientEntity patientEntity = PatientEntity.toPatientEntity(patientDTO);
        System.out.println("PatientService.save");
        patientRepository.save(patientEntity);
        System.out.println("patientDTO = " + patientDTO);


    }
}
