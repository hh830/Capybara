package com.codingrecipe.member.service.appointmentsService;

import com.codingrecipe.member.TimeRange;
import com.codingrecipe.member.entity.OperatingHours;
import com.codingrecipe.member.repository.appointmentsRepository.AppointmentsRepository;
import com.codingrecipe.member.repository.operationTimeRepository.OperationTimeRepository;

import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AvailableTimeService {
    @Autowired
    private OperationTimeRepository operationTimeRepository;

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    public Map<String, List<String>> getAvailableTimes(String hospitalId, String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        Map<String, List<String>> availableTimesMap = new HashMap<>();

        List<String> availableTimes = getAvailableTimesForSingleDate(hospitalId, date);
        availableTimesMap.put(date.toString(), availableTimes);

        return availableTimesMap;
    }
    //날짜에서 예약 가능 시간 범위 생성
    private List<String> getAvailableTimesForSingleDate(String hospitalId, LocalDate date) {
        String dayOfWeek = date.getDayOfWeek().toString().toUpperCase();
        List<OperatingHours> operatingHoursList = operationTimeRepository.findByHospital_BusinessIdAndDayOfWeek(hospitalId, dayOfWeek);
        List<String> availableTimes = new ArrayList<>();

        for (OperatingHours hours : operatingHoursList) {
            TimeRange operatingRange = TimeRange.fromString(hours.getOpeningHours());

            // 운영 시간이 없는 경우 예약 가능한 시간 없음을 나타냄
            if (operatingRange == null) {
                return Collections.emptyList();
            }

            TimeRange breakRange = TimeRange.fromString(hours.getBreakTime());

            // 운영 시간에서 휴게 시간을 제외한 시간 범위를 계산합니다.
            List<TimeRange> availableTimeRanges = operatingRange.subtract(breakRange);

            // 각 시간 범위에 대해 예약 가능한 시간 슬롯을 추가합니다.
            for (TimeRange range : availableTimeRanges) {
                LocalTime startTime = range.getStartTime();

                // startTime이 정시에 도달할 때까지 기다립니다.
                while (startTime.getMinute() != 0) {
                    startTime = startTime.plusMinutes(1);
                }

                // 정시부터 한 시간 간격으로 시간 슬롯을 설정합니다.
                while (!startTime.isAfter(range.getEndTime()) && startTime.plusHours(1).isBefore(range.getEndTime()) || startTime.plusHours(1).equals(range.getEndTime())) {
                    availableTimes.add(startTime.toString());
                    startTime = startTime.plusHours(1); // 한 시간씩 증가
                }
            }
        }

        return availableTimes;
    }



    private boolean isTimeSlotBooked(String hospitalId, LocalDate date, String timeSlot) {
        // 예약된 시간을 확인하는 로직을 구현합니다.
        // 예: reservationRepository.isTimeSlotBooked(hospitalId, date, timeSlot);
        return false; // 예시 코드
    }
}