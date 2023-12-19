package com.codingrecipe.member.service.appointmentsService;

import com.codingrecipe.member.dto.appointmentsDTO.AppointmentsDTO;
import com.codingrecipe.member.entity.Appointments;
import com.codingrecipe.member.entity.Patients;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.AppointmentsRepository;
import com.codingrecipe.member.repository.hospitalRepository.HospitalRepository;
import com.codingrecipe.member.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockTimeoutException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AppointmentsService {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    // 매일 자정에 실행되는 스케줄링된 작업
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 (0시 0분)에 실행
    public void updateStatusForPastAppointments() {
        LocalDate currentDate = LocalDate.now();
        appointmentsRepository.updateStatusForPastAppointments(currentDate);
    }

    @Transactional
    public ResponseEntity<?> createReservation(AppointmentsDTO appointmentsDTO, String userId) {
        LocalDate date = appointmentsDTO.getDate();
        LocalTime time = appointmentsDTO.getTime();
        String hospitalId = appointmentsDTO.getHospitalId();
        LocalDateTime appointmentDateTime = LocalDateTime.of(date, time);

        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "과거 날짜 및 시간에는 예약할 수 없습니다.");
        }
        try {
            // 예약 부도 확인 및 패널티 적용
            if (isPenaltyApplied(userId)) {
                throw new CustomValidationException(HttpStatus.FORBIDDEN.value(), "예약 부도 패널티 적용 중 ");
            }
            // 최대 예약 건수 확인 (상태가 '진료전'인 경우에만)
            if (isMaximumReservationsReached(userId)) {
                throw new CustomValidationException(HttpStatus.FORBIDDEN.value(), "최대 예약 2건 초과");
            }

            // 해당 병원에서 이미 예약한 날짜 확인 ( 예약한 날짜의 다른 시간대 예약 불가 )
            if (isDuplicateReservation1(userId, date, hospitalId)) {
                throw new CustomValidationException(HttpStatus.FORBIDDEN.value(), "이미 예약한 날짜입니다");
            }

            // 모든 병원에서 이미 예약한 날짜와 시간 확인
            if (isDuplicateReservation(userId, date, time)) {
                throw new CustomValidationException(HttpStatus.FORBIDDEN.value(), "다른 병원과 예약 시간이 겹칩니다.");
            }

            // 예약 시간대 검증 - 비관적 락
            if (!isTimeSlotAvailable(appointmentsDTO.getHospitalId(), appointmentsDTO.getDate(), appointmentsDTO.getTime())) {
                throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "예약 불가능한 시간대입니다.");
            }

            // 예약 저장
            appointmentsRepository.save(convertDtoToEntity(appointmentsDTO, userId));
            Patients patients = patientRepository.findByPatientId(userId);
            String userName = patients.getName();
            Map<String, Object> response = new HashMap<>();
            response.put("userName", userName);
            response.put("hospitalId", hospitalId);
            response.put("date", date);
            response.put("time", time);

            return ResponseEntity.ok(response);

        } catch (LockTimeoutException e) {
            // 잠금 시간 초과 예외 처리
            throw new CustomValidationException(HttpStatus.REQUEST_TIMEOUT.value(), "예약 시도 중 시간 초과가 발생했습니다. 잠시 후 다시 시도해주세요.");
        } catch (PessimisticLockingFailureException e) {
            // 데드락 혹은 기타 비관적 잠금 실패 처리
            throw new CustomValidationException(HttpStatus.CONFLICT.value(), "시스템 내부 오류로 인한 예약 실패. 다시 시도해주세요.");
        }
    }

    private Appointments convertDtoToEntity(AppointmentsDTO appointmentsDTO, String userId) {
        Appointments appointments = new Appointments();

        // DTO -> 엔티티 전환
        appointments.setUserId(userId, patientRepository);
        appointments.setAppointmentDate(appointmentsDTO.getDate());
        appointments.setAppointmentTime(appointmentsDTO.getTime());
        appointments.setHospitalId(appointmentsDTO.getHospitalId(), hospitalRepository);
        appointments.setStatus("진료전");

        return appointments;
    }

    private boolean isPenaltyApplied(String userId) {
        // 2달 이내에 "예약부도" 상태인 예약 확인
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(2);
        int penaltyCount = appointmentsRepository.countByPatients_PatientIdAndStatusAndAppointmentDateAfter(userId, "예약부도", oneMonthAgo);
        return penaltyCount >= 5;
    }

    private boolean isDuplicateReservation(String userId, LocalDate date, LocalTime time) {
        // 모든 병원에서 동일한 날짜와 시간에 대한 예약 확인
        return appointmentsRepository.existsByPatients_PatientIdAndAppointmentDateAndAppointmentTime(userId, date, time);
    }

    private boolean isDuplicateReservation1(String userId, LocalDate date, String hospitalId) {
        // 이미 예약한 날짜에 해당 병원에서의 중복 예약 확인 ( 해당 병원의 해당 날짜에서 다른 시간대 예약 불가 )
        return appointmentsRepository.existsByPatients_PatientIdAndHospital_BusinessIdAndAppointmentDate(userId, hospitalId, date);
    }

    //예약 가능 시간대 확인 - 비관적 락
    @Transactional(readOnly = true)
    boolean isTimeSlotAvailable(String hospitalId, LocalDate date, LocalTime time) {
        //int seat = availableTimeService.calculateAvailableSlots(hospitalId, date, time);
        int seat = 3 - appointmentsRepository.countAppointmentsForTimeSlot(hospitalId, date, time);
        if(seat > 0) // 남은자리가 0 이상이면 예약 가능
        {
            return true;
        }
        return false;
    }

    private boolean isMaximumReservationsReached(String userId) {
        int maxReservationCount = 2;
        int userActiveReservationCount = appointmentsRepository.countByPatients_PatientIdAndStatus(userId, "진료전");
        return userActiveReservationCount >= maxReservationCount;
    }

}
