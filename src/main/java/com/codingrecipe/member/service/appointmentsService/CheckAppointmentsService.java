package com.codingrecipe.member.service.appointmentsService;

import com.codingrecipe.member.dto.appointmentsDTO.AppointmentsDTO;
import com.codingrecipe.member.entity.Appointments;
import com.codingrecipe.member.repository.appointmentsRepository.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckAppointmentsService {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    public List<AppointmentsDTO> getReservationsByUserId(String userId) {

        try{
            List<Appointments> appointments = appointmentsRepository.findByPatients_PatientId(userId);
            List<AppointmentsDTO> appointmentDTOs = new ArrayList<>();

            for (Appointments appointment : appointments) {
                AppointmentsDTO dto = new AppointmentsDTO(
                        appointment.getHospital().getBusinessId(),
                        appointment.getHospital().getName(),
                        appointment.getHospital().getAddress(),
                        (long) appointment.getAppointmentId(),
                        appointment.getAppointmentDate(),
                        appointment.getAppointmentTime(),
                        appointment.getStatus(),
                        appointment.getPatients().getName()

                );
                appointmentDTOs.add(dto);
            }
            return appointmentDTOs;

        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }
}
