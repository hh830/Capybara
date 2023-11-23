package com.codingrecipe.member.repository.hospitalRepository;

import com.codingrecipe.member.entity.Hospital;
import com.codingrecipe.member.entity.OperatingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationTimeRepository extends JpaRepository<OperatingHours, Long>, OperationTimeRepositoryCustom {
    // 특정 병원의 운영 시간 조회
    List<OperatingHours> findByHospitalBusinessId(Hospital hospital);

    // 요일별 운영 시간 조회
    List<OperatingHours> findByDayOfWeek(String dayOfWeek);
}
