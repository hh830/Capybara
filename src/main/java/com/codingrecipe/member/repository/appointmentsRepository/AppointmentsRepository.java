package com.codingrecipe.member.repository.appointmentsRepository;

import com.codingrecipe.member.entity.Appointments;
import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {
    //int countByAppointmentDateAndAppointmentTime(LocalDate appointmentDate, LocalTime appointmentTime, String businessId);
    //boolean existsByHospital_BusinessIdAndAppointmentDateAndAppointmentTime(String businessId, LocalDate appointmentDate, LocalTime appointmentTime);
    int countByHospital_BusinessIdAndAppointmentDateAndAppointmentTime(String businessId, LocalDate appointmentDate, LocalTime startTime);

    int countByPatients_PatientIdAndHospital_BusinessIdAndStatus(String userId, String hospitalId, String 진료전);

    int countByPatients_PatientIdAndStatusAndAppointmentDateAfter(String userId, String 예약부도, LocalDate oneMonthAgo);

    boolean existsByPatients_PatientIdAndAppointmentDateAndAppointmentTime(String userId, LocalDate date, LocalTime time);

    boolean existsByPatients_PatientIdAndHospital_BusinessIdAndAppointmentDate(String userId, String hospitalId, LocalDate date);

    int countByPatients_PatientIdAndStatus(String userId, String 진료전);

    @Modifying
    @Query("UPDATE Appointments a SET a.status = '진료완료' WHERE a.appointmentDate < :currentDate AND a.status = '진료전'")
    int updateStatusForPastAppointments(LocalDate currentDate);
}