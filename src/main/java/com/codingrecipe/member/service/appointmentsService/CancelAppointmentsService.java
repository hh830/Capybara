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
                Appointments app = appointment.get();
                // 진료 전 상태인지 확인
                if ("진료전".equals(app.getStatus())) {
                    appointmentsRepository.delete(app);
                    return true;
                } else {
                    // 예약 상태가 진료 전이 아닐 경우
                    throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "진료완료된 예약건입니다.");
                }
            } else {
                return false;
            }
        } catch (Exception e){
            throw new CustomValidationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }


}