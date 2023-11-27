package com.codingrecipe.member.repository.likesRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepositoryCustom {
    long getLikesCountForHospital(String businessId);

}
