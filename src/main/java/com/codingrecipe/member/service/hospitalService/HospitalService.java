package com.codingrecipe.member.service.hospitalService;

import com.codingrecipe.member.dto.hospitalDTO.HospitalDTO;
import com.codingrecipe.member.entity.Hospital;
import com.codingrecipe.member.repository.hospitalRepository.HospitalRepository;
import com.codingrecipe.member.repository.hospitalRepository.HospitalRepositoryCustom;
import com.codingrecipe.member.repository.hospitalRepository.LikesRepository;
import com.codingrecipe.member.repository.hospitalRepository.OperationTimeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final OperationTimeRepository operationTimeRepository;
    private final LikesRepository likesRepository;

    public HospitalService(HospitalRepository hospitalRepository,
                           OperationTimeRepository operationTimeRepository,
                           LikesRepository likesRepository) {
        this.hospitalRepository = hospitalRepository;
        this.operationTimeRepository = operationTimeRepository;
        this.likesRepository = likesRepository;
    }

    public List<HospitalDTO> searchHospitalsWithDetails(String query, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page,size);

        // 오늘 날짜에 해당하는 요일을 한글로 구합니다.
            String today = new SimpleDateFormat("EEEE", Locale.KOREAN).format(new Date());
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
                        hospital.getName(),
                        hospital.getAddress(),
                        hospital.getDepartment(),
                        operatingHours,
                        likesCount
                );
            }).collect(Collectors.toList());

    }


    public List<HospitalDTO> searchByDepartment(String department, int page, int size) {
        // 오늘 날짜에 해당하는 요일을 한글로 구합니다.
        String today = new SimpleDateFormat("EEEE", Locale.KOREAN).format(new Date());
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Hospital> hospitals = hospitalRepository.findByDepartment(department, pageRequest);

        // 병원 목록을 HospitalDTO 목록으로 변환
        return hospitals.stream().map(hospital -> {
            // 운영시간 및 좋아요 수 조회
            String operatingHours = operationTimeRepository.findOperatingHoursByHospitalIdAndDay(hospital.getBusinessId(), today);
            long likesCount = likesRepository.getLikesCountForHospital(hospital.getBusinessId());

            // HospitalDTO 객체 생성
            return new HospitalDTO(
                    hospital.getName(),
                    hospital.getAddress(),
                    hospital.getDepartment(),
                    operatingHours,
                    likesCount
            );
        }).collect(Collectors.toList());
    }

    public List<HospitalDTO> getAllHospitals(int limit) {
        return null;
    }


}
