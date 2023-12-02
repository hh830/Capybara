package com.codingrecipe.member.service.hospitalService;

import com.codingrecipe.member.TimeRange;
import com.codingrecipe.member.entity.Hospital;
import com.codingrecipe.member.entity.OperatingHours;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.appointmentsRepository.AppointmentsRepository;
import com.codingrecipe.member.repository.hospitalRepository.HospitalRepository;
import com.codingrecipe.member.repository.operationTimeRepository.OperationTimeRepository;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AvailableTimeService {
    @Autowired
    private OperationTimeRepository operationTimeRepository;

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    public Map<String, Object> getAvailableTimes(String hospitalId, String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        //Map<String, List<Map<String, Object>>> availableTimesMap = new HashMap<>();
        Map<String, Object> response = new HashMap<>();

        List<Map<String, Object>> availableTimes = getAvailableTimesForSingleDate(hospitalId, date);

        String hospitalName = hospitalRepository.findByBusinessId(hospitalId);

        // JSON 객체에 병원 이름과 사용 가능한 시간 추가
        response.put("hospitalName", hospitalName);
        response.put(date.toString(), availableTimes);

        return response;
    }

    public List<Map<String, Object>> getAvailableTimesForSingleDate(String hospitalId, LocalDate date) {
        try {
            String dayOfWeek = date.getDayOfWeek().toString().toUpperCase();
            //운영시간 확인
            List<OperatingHours> operatingHoursList = operationTimeRepository.findByHospital_BusinessIdAndDayOfWeek(hospitalId, dayOfWeek);
            List<Map<String, Object>> availableTimesWithSlots = new ArrayList<>();

            for (OperatingHours hours : operatingHoursList) {
                TimeRange operatingRange = TimeRange.fromString(hours.getOpeningHours());
                if (operatingRange == null) { //운영시간이 없으면 빈 리스트
                    return Collections.emptyList();
                }

                TimeRange breakRange = TimeRange.fromString(hours.getBreakTime());
                List<TimeRange> availableTimeRanges = operatingRange.subtract(breakRange);

                for (TimeRange range : availableTimeRanges) {
                    LocalTime startTime = range.getStartTime();
                    while (startTime.getMinute() != 0) {
                        startTime = startTime.plusMinutes(1);
                    }
                    //남는자리 있는지
                    while (!startTime.isAfter(range.getEndTime()) && startTime.plusHours(1).isBefore(range.getEndTime()) || startTime.plusHours(1).equals(range.getEndTime())) {
                        int availableSlots = calculateAvailableSlots(hospitalId, date, startTime);
                        Map<String, Object> timeWithSlots = new HashMap<>();

                        timeWithSlots.put("time", startTime.toString());
                        timeWithSlots.put("availableSlots", Math.max(availableSlots, 0));
                        availableTimesWithSlots.add(timeWithSlots);
                        startTime = startTime.plusHours(1);
                    }
                }
            }

            return availableTimesWithSlots;

        } catch(CustomValidationException e){
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "조회 오류");
        } catch (Exception e){
            throw new CustomValidationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "조회 실패");
        }
    }

    //@Transactional
    //남는자리 있는지 계산
    public int calculateAvailableSlots(String hospitalId, LocalDate date, LocalTime startTime) {
        return 3 - appointmentsRepository.countByHospital_BusinessIdAndAppointmentDateAndAppointmentTime(hospitalId, date, startTime);
    }
}
