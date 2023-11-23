package com.codingrecipe.member.repository.hospitalRepository;

import com.codingrecipe.member.entity.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepositoryCustom {
    Page<Hospital> searchWithDynamicQuery(String query, Pageable pageable);

}
