package com.codingrecipe.member.service.appointmentsService;

import com.codingrecipe.member.entity.Appointments;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.appointmentsRepository.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CancelAppointmentsService {
     @Autowired
     private AppointmentsRepository appointmentsRepository;

     @Transactional
     public boolean cancelAppointment(int appointmentId, String userId) {
         try {
             Optional<Appointments> appointment = appointmentsRepository.findByPatients_PatientIdAndAppointmentId(appointmentId, userId);
             if (appointment.isPresent()) {
                 appointmentsRepository.delete(appointment.get());
                 return true;
             } else {
                 return false;
             }
         }catch (Exception e){
             throw new CustomValidationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
         }
    }
}