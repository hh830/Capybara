package com.codingrecipe.member.repository.likesRepository;

import com.codingrecipe.member.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>, LikesRepositoryCustom {
    // 특정 병원의 좋아요 수 조회
    Long countByHospitalBusinessId(String businessId);

    // 사용자별 좋아요 목록 조회
    List<Likes> findByPatientsPatientId(String patientId);

    boolean existsByPatients_PatientIdAndHospital_BusinessId(String patientId, String businessId);

}
