package com.codingrecipe.member.repository.operationTimeRepository;

import com.codingrecipe.member.entity.OperatingHours;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationTimeRepositoryCustom {
    String findOperatingHoursByHospitalIdAndDay(String businessId, String today);

    String findBreakTimeByHospitalIdAndDay(String businessId, String dayOfWeek);

    List<OperatingHours> findByHospitalIdAndDayOfWeek(Long hospitalId, String dayOfWeek);

    List<OperatingHours> findOperatingHoursByHospitalIdAndDate(String hospitalId, String date);

}
