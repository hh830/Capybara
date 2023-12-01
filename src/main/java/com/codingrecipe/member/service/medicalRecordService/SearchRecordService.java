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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchRecordService {

    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    public List<RecordDTO> searchRecords(String userId, String hospitalName, LocalDate date) {
        try {
            List<MedicalRecords> medicalRecords = new ArrayList<>(); // 빈 리스트로 초기화
            if (hospitalName != null && date != null) {
                medicalRecords = medicalRecordsRepository.findByPatients_PatientIdAndDoctors_Hospital_NameLikeAndRecordDateOrderByRecordDateDesc(userId, "%" + hospitalName + "%", date);

            } else if (hospitalName == null && date != null) {
                medicalRecords = medicalRecordsRepository.findByPatients_PatientIdAndRecordDateOrderByRecordDateDesc(userId, date);

            } else if (hospitalName != null) {
                medicalRecords = medicalRecordsRepository.findByPatients_PatientIdAndDoctors_Hospital_NameLikeOrderByRecordDateDesc(userId, "%" + hospitalName + "%");
            } else {
                throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "검색어나 날짜를 입력해주세요");
            }
            // Appointments 엔티티를 AppointmentsDTO로 변환
            return medicalRecords.stream().map(this::convertToDTO).collect(Collectors.toList());

        } catch (Exception e){
            throw new IllegalStateException();
        }
    }

    private RecordDTO convertToDTO(MedicalRecords medicalRecords) {
        RecordDTO dto = new RecordDTO(
                medicalRecords.getRecordId(),
                medicalRecords.getRecordDate(),
                medicalRecords.getDoctors().getName(),
                medicalRecords.getDoctors().getHospital().getName()
        );
        return dto;
    }

}
