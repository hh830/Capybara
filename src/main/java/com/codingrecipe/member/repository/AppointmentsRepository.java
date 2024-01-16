package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.Appointments;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, String> {

    @Query("SELECT COUNT(a) FROM Appointments a WHERE a.hospital.businessId = :businessId AND a.appointmentDate = :appointmentDate AND a.appointmentTime = :appointmentTime")
    int countByHospital_BusinessIdAndAppointmentDateAndAppointmentTime(@Param("businessId") String businessId, @Param("appointmentDate") LocalDate appointmentDate, @Param("appointmentTime") LocalTime appointmentTime);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "5000") }) // 5초 타임아웃 설정
    @Query("SELECT COUNT(a) FROM Appointments a WHERE a.hospital.businessId = :businessId AND a.appointmentDate = :appointmentDate AND a.appointmentTime = :startTime")
    int countAppointmentsForTimeSlot(@Param("businessId") String businessId,
                                     @Param("appointmentDate") LocalDate appointmentDate,
                                     @Param("startTime") LocalTime startTime);

    int countByPatients_PatientIdAndStatusAndAppointmentDateAfter(String userId, String 예약부도, LocalDate oneMonthAgo);

    boolean existsByPatients_PatientIdAndAppointmentDateAndAppointmentTime(String userId, LocalDate date, LocalTime time);

    boolean existsByPatients_PatientIdAndHospital_BusinessIdAndAppointmentDate(String userId, String hospitalId, LocalDate date);

    int countByPatients_PatientIdAndStatus(String userId, String 진료전);

    @Modifying
    @Query("UPDATE Appointments a SET a.status = '진료완료' WHERE a.appointmentDate < :currentDate AND a.status = '진료전'")
    int updateStatusForPastAppointments(LocalDate currentDate);

    List<Appointments> findByPatients_PatientIdOrderByAppointmentDateDesc(String patientId);

//    @Query("SELECT a FROM Appointments a JOIN a.patients p JOIN a.hospital h WHERE p.patientId = :patientId AND h.name LIKE :name AND a.appointmentDate = :appointmentDate")
//    List<Appointments> findByPatients_PatientIdAndHospital_NameLikeAndAppointmentDate(@Param("patientId") String patientId, @Param("name") String name, @Param("appointmentDate") LocalDate appointmentDate);
//
//    @Query("SELECT a FROM Appointments a JOIN a.patients p JOIN a.hospital h WHERE p.patientId = :patientId AND a.appointmentDate = :appointmentDate")
//    List<Appointments> findByPatients_PatientIdAndAppointmentDate(@Param("patientId") String patientId, @Param("appointmentDate") LocalDate appointmentDate);
//
//    @Query("SELECT a FROM Appointments a JOIN a.patients p JOIN a.hospital h WHERE p.patientId = :patientId AND h.name LIKE :name")
//    List<Appointments> findByPatients_PatientIdAndHospital_Name(@Param("patientId") String patientId, @Param("name") String name);

    @Query("SELECT a FROM Appointments a JOIN a.patients p JOIN a.hospital h WHERE p.patientId = :patientId AND h.name LIKE :name AND a.appointmentDate = :appointmentDate ORDER BY a.appointmentDate DESC")
    List<Appointments> findByPatients_PatientIdAndHospital_NameLikeAndAppointmentDate(@Param("patientId") String patientId, @Param("name") String name, @Param("appointmentDate") LocalDate appointmentDate);

    @Query("SELECT a FROM Appointments a JOIN a.patients p JOIN a.hospital h WHERE p.patientId = :patientId AND a.appointmentDate = :appointmentDate ORDER BY a.appointmentDate DESC")
    List<Appointments> findByPatients_PatientIdAndAppointmentDate(@Param("patientId") String patientId, @Param("appointmentDate") LocalDate appointmentDate);

    @Query("SELECT a FROM Appointments a JOIN a.patients p JOIN a.hospital h WHERE p.patientId = :patientId AND h.name LIKE :name ORDER BY a.appointmentDate DESC")
    List<Appointments> findByPatients_PatientIdAndHospital_Name(@Param("patientId") String patientId, @Param("name") String name);

    //예약 취소하기 위해
    @Query("SELECT a FROM Appointments a WHERE a.patients.patientId = :patientId AND a.appointmentId = :appointmentId")
    Optional<Appointments> findByPatients_PatientIdAndAppointmentId(@Param("appointmentId") int appointmentId, @Param("patientId") String patientId);
}