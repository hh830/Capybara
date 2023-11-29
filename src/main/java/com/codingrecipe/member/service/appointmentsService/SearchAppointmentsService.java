package com.codingrecipe.member.service.appointmentsService;
import com.codingrecipe.member.exception.CustomValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.codingrecipe.member.dto.appointmentsDTO.AppointmentsDTO;
import com.codingrecipe.member.entity.Appointments;
import com.codingrecipe.member.repository.appointmentsRepository.AppointmentsRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchAppointmentsService {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    public List<AppointmentsDTO> searchAppointments(String userId, String hospitalName, LocalDate date) {

        try {
            List<Appointments> appointments = new ArrayList<>(); // 빈 리스트로 초기화
            if (hospitalName != null && date != null) {
                appointments = appointmentsRepository.findByPatients_PatientIdAndHospital_NameLikeAndAppointmentDate(userId, "%" + hospitalName + "%", date);

            } else if (hospitalName == null && date != null) {
                appointments = appointmentsRepository.findByPatients_PatientIdAndAppointmentDate(userId, date);

            } else if (hospitalName != null) {
                appointments = appointmentsRepository.findByPatients_PatientIdAndHospital_Name(userId, "%" + hospitalName + "%");
            } else {
                throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "검색어나 날짜를 입력해주세요");
            }
            // Appointments 엔티티를 AppointmentsDTO로 변환
            return appointments.stream().map(this::convertToDTO).collect(Collectors.toList());

        } catch (Exception e){
            throw new IllegalStateException();
        }
    }

    private AppointmentsDTO convertToDTO(Appointments appointment) {
        AppointmentsDTO dto = new AppointmentsDTO(
                appointment.getHospital().getBusinessId(),
                appointment.getHospital().getName(),
                appointment.getHospital().getAddress(),
                (long) appointment.getAppointmentId(), // int -> Long 변환
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getStatus(),
                appointment.getPatients().getName()
        );
        return dto;
    }
}
