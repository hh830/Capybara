package com.codingrecipe.member.repository.hospitalRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface OperationTimeRepositoryCustom {
    String findOperatingHoursByHospitalIdAndDay(String businessId, String today);
}
