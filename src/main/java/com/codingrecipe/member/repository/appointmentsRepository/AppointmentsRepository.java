package com.codingrecipe.member.repository.appointmentsRepository;

import com.codingrecipe.member.entity.Appointments;
import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {
    boolean existsByHospital_BusinessIdAndAppointmentDateAndAppointmentTime(String businessId, LocalDate appointmentDate, LocalTime appointmentTime);
}