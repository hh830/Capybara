package com.codingrecipe.member.repository.hospitalRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepositoryCustom {
    long getLikesCountForHospital(String businessId);
}
