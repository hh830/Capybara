package com.codingrecipe.member.service.hospitalService;

import com.codingrecipe.member.dto.hospitalDTO.HospitalDTO;
import com.codingrecipe.member.entity.Hospital;
import com.codingrecipe.member.repository.hospitalRepository.HospitalRepository;
import com.codingrecipe.member.repository.likesRepository.LikesRepository;
import com.codingrecipe.member.repository.operationTimeRepository.OperationTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private OperationTimeRepository operationTimeRepository;

    @Autowired
    private LikesRepository likesRepository;


    public List<HospitalDTO> searchHospitalsWithDetails(String query, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page,size);

        // 오늘 날짜에 해당하는 요일을 한글로 구합니다.
            String today = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());
            System.out.println("query = " + query);
            // 동적 쿼리로 병원 검색
            Page<Hospital> hospitals = hospitalRepository.searchWithDynamicQuery(query, pageRequest);
            System.out.println("hospitals = " + hospitals);
            // 병원 목록을 HospitalDTO 목록으로 변환
            return hospitals.stream().map(hospital -> {
                // 운영시간 및 좋아요 수 조회
                String operatingHours = operationTimeRepository.findOperatingHoursByHospitalIdAndDay(hospital.getBusinessId(), today);
                long likesCount = likesRepository.getLikesCountForHospital(hospital.getBusinessId());

                // HospitalDTO 객체 생성
                return new HospitalDTO(
                        hospital.getBusinessId(),
                        hospital.getName(),
                        hospital.getPhoneNumber(),
                        hospital.getAddress(),
                        hospital.getDepartment(),
                        operatingHours,
                        likesCount
                );
            }).collect(Collectors.toList());

    }


    public List<HospitalDTO> searchByDepartment(String department, int page, int size) {
        // 오늘 날짜에 해당하는 요일을 한글로 구합니다.
        String today = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Hospital> hospitals = hospitalRepository.findByDepartment(department, pageRequest);

        // 병원 목록을 HospitalDTO 목록으로 변환
        return hospitals.stream().map(hospital -> {
            // 운영시간 및 좋아요 수 조회
            String operatingHours = operationTimeRepository.findOperatingHoursByHospitalIdAndDay(hospital.getBusinessId(), today);
            long likesCount = likesRepository.getLikesCountForHospital(hospital.getBusinessId());

            // HospitalDTO 객체 생성
            return new HospitalDTO(
                    hospital.getBusinessId(),
                    hospital.getName(),
                    hospital.getPhoneNumber(),
                    hospital.getAddress(),
                    hospital.getDepartment(),
                    operatingHours,
                    likesCount
            );
        }).collect(Collectors.toList());
    }

    public List<HospitalDTO> getAllHospitals(int page, int size) {

        String today = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Hospital> hospitals = hospitalRepository.findAll(pageRequest);

        // 병원 목록을 HospitalDTO 목록으로 변환
        return hospitals.stream().map(hospital -> {
            // 운영시간 및 좋아요 수 조회
            String operatingHours = operationTimeRepository.findOperatingHoursByHospitalIdAndDay(hospital.getBusinessId(), today);
            long likesCount = likesRepository.getLikesCountForHospital(hospital.getBusinessId());

            // HospitalDTO 객체 생성
            return new HospitalDTO(
                    hospital.getBusinessId(),
                    hospital.getName(),
                    hospital.getPhoneNumber(),
                    hospital.getAddress(),
                    hospital.getDepartment(),
                    operatingHours,
                    likesCount
            );
        }).collect(Collectors.toList());

    }
}
