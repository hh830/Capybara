package com.codingrecipe.member.service.hospitalService;

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

    public Map<String, List<Map<String, Object>>> getAvailableTimes(String hospitalId, String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        Map<String, List<Map<String, Object>>> availableTimesMap = new HashMap<>();

        List<Map<String, Object>> availableTimes = getAvailableTimesForSingleDate(hospitalId, date);
        availableTimesMap.put(date.toString(), availableTimes);

        return availableTimesMap;
    }

    public List<Map<String, Object>> getAvailableTimesForSingleDate(String hospitalId, LocalDate date) {
        String dayOfWeek = date.getDayOfWeek().toString().toUpperCase();
        List<OperatingHours> operatingHoursList = operationTimeRepository.findByHospital_BusinessIdAndDayOfWeek(hospitalId, dayOfWeek);
        List<Map<String, Object>> availableTimesWithSlots = new ArrayList<>();

        for (OperatingHours hours : operatingHoursList) {
            TimeRange operatingRange = TimeRange.fromString(hours.getOpeningHours());
            if (operatingRange == null) {
                return Collections.emptyList();
            }

            TimeRange breakRange = TimeRange.fromString(hours.getBreakTime());
            List<TimeRange> availableTimeRanges = operatingRange.subtract(breakRange);

            for (TimeRange range : availableTimeRanges) {
                LocalTime startTime = range.getStartTime();
                while (startTime.getMinute() != 0) {
                    startTime = startTime.plusMinutes(1);
                }

                while (!startTime.isAfter(range.getEndTime()) && startTime.plusHours(1).isBefore(range.getEndTime()) || startTime.plusHours(1).equals(range.getEndTime())) {
                    int availableSlots = 3 - appointmentsRepository.countByHospital_BusinessIdAndAppointmentDateAndAppointmentTime(hospitalId, date, startTime);
                    Map<String, Object> timeWithSlots = new HashMap<>();
                    timeWithSlots.put("time", startTime.toString());
                    timeWithSlots.put("availableSlots", Math.max(availableSlots, 0));

                    availableTimesWithSlots.add(timeWithSlots);
                    startTime = startTime.plusHours(1);
                }
            }
        }

        return availableTimesWithSlots;
    }
}
