package com.codingrecipe.member.repository.hospitalRepository;

import com.codingrecipe.member.dto.hospitalDTO.HospitalDTO;
import com.codingrecipe.member.entity.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, String>, HospitalRepositoryCustom {

    Page<Hospital> searchWithDynamicQuery(String query, Pageable pageable);
    Page<Hospital> findByDepartment(String department, Pageable pageable);

    Optional<Hospital> findById(String businessId);

    Optional<Hospital> findByBusinessId(String businessId);
}