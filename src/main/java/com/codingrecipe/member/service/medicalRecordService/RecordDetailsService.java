package com.codingrecipe.member.service.medicalRecordService;

import com.codingrecipe.member.dto.doctorDTO.DoctorDTO;
import com.codingrecipe.member.dto.hospitalDTO.HospitalDetailsDTO;
import com.codingrecipe.member.dto.medicalRecordDTO.RecordDTO;
import com.codingrecipe.member.dto.medicalRecordDTO.RecordDetailsDTO;
import com.codingrecipe.member.entity.Doctors;
import com.codingrecipe.member.entity.Hospital;
import com.codingrecipe.member.entity.MedicalRecords;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.doctorRepository.DoctorRepository;
import com.codingrecipe.member.repository.likesRepository.LikesRepository;
import com.codingrecipe.member.repository.medicalRecordRepository.MedicalRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
