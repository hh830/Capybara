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

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer>, HospitalRepositoryCustom {
    // 기존의 메소드들...
    //List<Hospital> findByDepartment(String department, Pageable pageable);
//    @Query("SELECT new com.codingrecipe.member.dto.hospitalDTO.HospitalDTO(h.name, h.address, h.department, ot.openingHours, COUNT(l)) " +
//            "FROM Hospital h " +
//            "LEFT JOIN OperatingHours ot ON h.businessId = ot.hospital.businessId AND ot.dayOfWeek = :today " +
//            "LEFT JOIN Likes l ON h.businessId = l.hospital.businessId " +
//            "WHERE (:query IS NULL OR h.name LIKE CONCAT('%', :query, '%') OR h.department LIKE CONCAT('%', :query, '%') OR h.address LIKE CONCAT('%', :query, '%')) " +
//            "GROUP BY h.name, h.address, h.department, h.businessId, ot.openingHours")
//    List<HospitalDTO> findHospitalsWithOperationTimesAndLikes(@Param("query") String query, @Param("today") String today);
    Page<Hospital> searchWithDynamicQuery(String query, Pageable pageable);
    Page<Hospital> findByDepartment(String department, Pageable pageable);

}