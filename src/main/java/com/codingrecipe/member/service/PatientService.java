package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.PatientDTO;
import com.codingrecipe.member.entity.PatientEntity;
import com.codingrecipe.member.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientDTO login(PatientDTO patientDTO) {
        /*
            1. 회원이 입력한 아이디로 db에서 조회
            2. db에서 조회한 비밀번호와 사용자가 입력한 비밀번호와 일치하는지 판단
        */
        Optional<PatientEntity> byPatientId = patientRepository.findByPatientId(patientDTO.getUserId());
        System.out.println("patientDTO = " + patientDTO);
        if(byPatientId.isPresent()){

            //조회결과가 있다
            PatientEntity patientEntity = byPatientId.get();
            if(patientDTO.getPassword().equals(patientEntity.getPassword()))
            {
                //비밀번호 일치
                //entity -> dto 변환 후 리턴
                PatientDTO dto = PatientDTO.toPatientDTO(patientEntity);
                System.out.println("patientDTO = " + patientDTO);
                return dto;
            }
            else{
                //비밀번호 불일치
                System.out.println("patientDTO = " + patientDTO);
                return null;
            }
        }else {
            return null;
        }
    }

    public void save(PatientDTO patientDTO) {
        //repository의 save 메서드 호출 (조건: entity객체 넘겨주기)
        //1. dto -> entity 변환
        //2. repository의 save 메서드 호출

        PatientEntity patientEntity = PatientEntity.toPatientEntity(patientDTO);
        if (patientRepository.findById(patientEntity.getPatientId()).isPresent()) {
            throw new DataIntegrityViolationException("이미 존재하는 ID입니다.");
        }

        patientRepository.save(patientEntity);


    }
}
