package com.codingrecipe.member.service.medicalRecordService;

import com.codingrecipe.member.dto.medicalRecordDTO.RecordDetailsDTO;
import com.codingrecipe.member.entity.MedicalRecords;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.likesRepository.LikesRepository;
import com.codingrecipe.member.repository.MedicalRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RecordDetailsService {

    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    @Autowired
    private LikesRepository likesRepository;

    public RecordDetailsDTO recordDetails(String userId, int recordId) {

        try {
            MedicalRecords medicalRecords = medicalRecordsRepository.findByRecordId(recordId);

            long likesCount = likesRepository.getLikesCountForHospital(medicalRecords.getDoctors().getHospital().getBusinessId());
            boolean isLikes = likesRepository.existsByPatients_PatientIdAndHospital_BusinessId(userId, medicalRecords.getDoctors().getHospital().getBusinessId());

            RecordDetailsDTO recordDetailsDTO = new RecordDetailsDTO(recordId, medicalRecords.getContent(), medicalRecords.getRecordDate(),
                    medicalRecords.getPatients().getName(), medicalRecords.getPatients().getBirthDate(), medicalRecords.getDoctors().getHospital().getBusinessId(),
                    medicalRecords.getDoctors().getHospital().getName(), medicalRecords.getDoctors().getHospital().getPhoneNumber(),
                    medicalRecords.getDoctors().getLicenseNumber(), medicalRecords.getDoctors().getName(),
                    isLikes, likesCount
            );


            return recordDetailsDTO;
        } catch(Exception e){
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "잘못된 요청");
        }
    }


}
