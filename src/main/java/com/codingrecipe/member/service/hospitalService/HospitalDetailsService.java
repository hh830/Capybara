package com.codingrecipe.member.service.hospitalService;

import com.codingrecipe.member.dto.doctorDTO.DoctorDTO;
import com.codingrecipe.member.dto.hospitalDTO.HospitalDetailsDTO;
import com.codingrecipe.member.entity.Doctors;
import com.codingrecipe.member.entity.Hospital;
import com.codingrecipe.member.repository.doctorRepository.DoctorRepository;
import com.codingrecipe.member.repository.hospitalRepository.HospitalRepository;
import com.codingrecipe.member.repository.likesRepository.LikesRepository;
import com.codingrecipe.member.repository.operationTimeRepository.OperationTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Service
public class HospitalDetailsService {
    // @Autowired 필드들...
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private OperationTimeRepository operationTimeRepository;

    private final DoctorRepository doctorRepository;

    public HospitalDetailsService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public HospitalDetailsDTO getHospitalDetails(String userId, String hospitalId) {
        String today = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(()-> new RuntimeException("Hospital not found"));
        String operatingHours = operationTimeRepository.findOperatingHoursByHospitalIdAndDay(hospitalId, today);
        String breakTime = operationTimeRepository.findBreakTimeByHospitalIdAndDay(hospitalId, today);

        long likesCount = likesRepository.getLikesCountForHospital(hospitalId);

        boolean isLikes = false;

        if(userId != null && !userId.isEmpty()) {
            isLikes = likesRepository.existsByPatients_PatientIdAndHospital_BusinessId(userId, hospitalId);
        }

        List<Doctors> doctorsList = doctorRepository.findByHospital_BusinessId(hospitalId);

        List<DoctorDTO> doctorDTOs = doctorsList.stream().map(doctor ->
                new DoctorDTO(doctor.getName(), doctor.getBiography(), doctor.getGender())
        ).collect(Collectors.toList());

        return new HospitalDetailsDTO(hospital, operatingHours, breakTime, likesCount, isLikes, doctorDTOs);
    }
}
