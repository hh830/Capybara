package com.codingrecipe.member.repository.likesRepository;

import com.codingrecipe.member.entity.Hospital;
import com.codingrecipe.member.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>, LikesRepositoryCustom {
    // 특정 병원의 좋아요 수 조회
    Long countByHospitalBusinessId(String businessId);

    // 사용자별 좋아요 목록 조회
    List<Likes> findByPatientsPatientId(String patientId);

    boolean existsByPatients_PatientIdAndHospital_BusinessId(String patientId, String businessId);

    boolean existsByHospital_BusinessIdAndPatients_PatientId(String businessId, String patientId);

    Likes findByHospital_BusinessIdAndPatients_PatientId(String businessId, String patientId);

    // 좋아요 수에 따라 병원을 정렬하고 상위 3개를 가져오는 쿼리 메서드
//    @Query("SELECT l.hospital, COUNT(l) FROM Likes l GROUP BY l.hospital ORDER BY COUNT(l) DESC")
//    List<Object[]> findTopHospitalByLikes();
    //@Query("SELECT a FROM Appointments a JOIN a.patients p JOIN a.hospital h WHERE p.patientId = :patientId AND h.name LIKE :name AND a.appointmentDate = :appointmentDate")

    @Query("SELECT l.hospital, COUNT(l) FROM Likes l JOIN l.hospital h GROUP BY l.hospital ORDER BY COUNT(l) DESC")
    List<Object[]> findTopHospitalsByLikes(Pageable pageable);
}
