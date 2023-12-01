package com.codingrecipe.member.service.medicalRecordService;

import com.codingrecipe.member.dto.appointmentsDTO.AppointmentsDTO;
import com.codingrecipe.member.dto.medicalRecordDTO.RecordDTO;
import com.codingrecipe.member.entity.Appointments;
import com.codingrecipe.member.entity.MedicalRecords;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.medicalRecordRepository.MedicalRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CheckRecordService {
    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    public List<RecordDTO> checkRecordService(String userId) {
        try {
            // 환자 ID로 모든 의료기록 조회
            List<MedicalRecords> medicalRecords = medicalRecordsRepository.findByPatients_PatientIdOrderByRecordDateDesc(userId);
            List<RecordDTO> recordDTOS = new ArrayList<>();

            for (MedicalRecords record : medicalRecords) {
                // 의사가 소속된 병원의 이름을 가져옴
                String hospitalName = record.getDoctors().getHospital().getName();

                // DTO 생성 및 추가
                RecordDTO dto = new RecordDTO(
                        record.getRecordId(),
                        record.getRecordDate(),
                        record.getDoctors().getName(),
                        hospitalName  // 병원 이름 추가
                );
                recordDTOS.add(dto);
            }
            return recordDTOS;

        } catch (Exception e){
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "잘못된 요청");
        }
    }
}
