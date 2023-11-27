package com.codingrecipe.member.repository.operationTimeRepository;

import com.codingrecipe.member.entity.Hospital;
import com.codingrecipe.member.entity.OperatingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface OperationTimeRepository extends JpaRepository<OperatingHours, Long>, OperationTimeRepositoryCustom {
    // 특정 병원의 운영 시간 조회
    // 병원 ID와 요일을 기준으로 운영 시간을 조회하는 메소드
    List<OperatingHours> findByHospital_BusinessIdAndDayOfWeek(String hospitalId, String dayOfWeek);
}

