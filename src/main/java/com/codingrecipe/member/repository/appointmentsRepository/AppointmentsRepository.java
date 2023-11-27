package com.codingrecipe.member.repository.appointmentsRepository;

import com.codingrecipe.member.entity.Appointments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "5000") }) // 5초 타임아웃 설정
    int countByHospital_BusinessIdAndAppointmentDateAndAppointmentTime(String businessId, LocalDate appointmentDate, LocalTime startTime);

    int countByPatients_PatientIdAndHospital_BusinessIdAndStatus(String userId, String hospitalId, String 진료전);

    int countByPatients_PatientIdAndStatusAndAppointmentDateAfter(String userId, String 예약부도, LocalDate oneMonthAgo);

    boolean existsByPatients_PatientIdAndAppointmentDateAndAppointmentTime(String userId, LocalDate date, LocalTime time);

    boolean existsByPatients_PatientIdAndHospital_BusinessIdAndAppointmentDate(String userId, String hospitalId, LocalDate date);

    int countByPatients_PatientIdAndStatus(String userId, String 진료전);

    @Modifying
    @Query("UPDATE Appointments a SET a.status = '진료완료' WHERE a.appointmentDate < :currentDate AND a.status = '진료전'")
    int updateStatusForPastAppointments(LocalDate currentDate);

    List<Appointments> findByPatients_PatientId(String patientId);


}