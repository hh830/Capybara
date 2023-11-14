package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, String> { //<엔티티 이름, 엔티티pk>
}
