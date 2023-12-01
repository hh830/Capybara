package com.codingrecipe.member.repository.doctorRepository;

import com.codingrecipe.member.entity.Doctors;
import com.codingrecipe.member.entity.Hospital;
import com.codingrecipe.member.entity.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctors, String> {
    List<Doctors> findByHospital_BusinessId(String businessId);
    Doctors findByLicenseNumber(String licenseNumber);
}
