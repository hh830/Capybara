package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patients, String> { //<엔티티 이름, 엔티티pk>
    //아이디로 회원정보 조회 (select * from member_table where member_email=?)
    Patients findByPatientId(String patientId);
    Optional<Patients> findOptionalByPatientId(String patientId);

    boolean existsByPatientId(String patientId);
}
