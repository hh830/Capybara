package com.codingrecipe.member.repository.medicalRecordRepository;

import com.codingrecipe.member.entity.Appointments;
import com.codingrecipe.member.entity.Doctors;
import com.codingrecipe.member.entity.MedicalRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicalRecordsRepository extends JpaRepository<MedicalRecords, String> {

    List<MedicalRecords> findByPatients_PatientIdOrderByRecordDateDesc(String userId);

    List<MedicalRecords> findByPatients_PatientIdAndDoctors_Hospital_NameLikeAndRecordDateOrderByRecordDateDesc(String patientId, String hospitalName, LocalDate date);

    List<MedicalRecords> findByPatients_PatientIdAndRecordDateOrderByRecordDateDesc(String patientId, LocalDate recordDate);

    List<MedicalRecords> findByPatients_PatientIdAndDoctors_Hospital_NameLikeOrderByRecordDateDesc(String patientId, String name);

    //recordDetails
    MedicalRecords findByRecordId(int recordId);
}
