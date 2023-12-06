package com.codingrecipe.member.service.hospitalService;

import com.codingrecipe.member.TimeRange;
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
import java.time.LocalTime;
import java.time.ZoneId;
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

    @Autowired
    private DoctorRepository doctorRepository;



    public HospitalDetailsDTO getHospitalDetails(String userId, String hospitalId) {
        String today = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(()-> new RuntimeException("Hospital not found"));
        String operatingHours = operationTimeRepository.findOperatingHoursByHospitalIdAndDay(hospitalId, today);

        String breakTime = operationTimeRepository.findBreakTimeByHospitalIdAndDay(hospitalId, today);
        String hospitalStatus = determineHospitalStatus(operatingHours, breakTime);

        long likesCount = likesRepository.getLikesCountForHospital(hospitalId);

        boolean isLikes = false;

        if(userId != null && !userId.isEmpty()) {
            isLikes = likesRepository.existsByPatients_PatientIdAndHospital_BusinessId(userId, hospitalId);
        }

        List<Doctors> doctorsList = doctorRepository.findByHospital_BusinessId(hospitalId);

        List<DoctorDTO> doctorDTOs = doctorsList.stream().map(doctor ->
                new DoctorDTO(doctor.getName(), doctor.getBiography(), doctor.getGender())
        ).collect(Collectors.toList());

        return new HospitalDetailsDTO(hospital, operatingHours, breakTime, likesCount, isLikes, hospitalStatus, doctorDTOs);
    }

    private String determineHospitalStatus(String operatingHours, String breakTime) {
        LocalTime currentTime = LocalTime.now(ZoneId.of("Asia/Seoul"));
        TimeRange operatingRange = TimeRange.fromString(operatingHours);
        TimeRange breakRange = TimeRange.fromString(breakTime);

        System.out.println("시간 = "+currentTime+operatingHours+breakTime);
        if (operatingRange == null) {
            return "오늘휴무";
        } else if (isCurrentTimeInRange(currentTime, breakRange)) {
            return "휴게시간";
        } else if (isCurrentTimeInRange(currentTime, operatingRange)) {
            return "영업중";
        }
        else if (currentTime.isBefore(operatingRange.getStartTime())) {
            return "영업전";
        } else if(currentTime.isAfter(operatingRange.getEndTime())){
            return "영업종료";
        }  else{
            return "영업정보없음";
        }
    }

    private boolean isCurrentTimeInRange(LocalTime currentTime, TimeRange range) {
        if (range == null) {
            return false;
        }
        return !currentTime.isBefore(range.getStartTime()) && !currentTime.isAfter(range.getEndTime());
    }
}
