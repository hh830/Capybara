package com.codingrecipe.member.controller.hospitalController;


import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.service.hospitalService.AvailableTimeService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reservations")
public class AvailableTimeController {
    @Autowired
    private AvailableTimeService availableTimeService;

    @GetMapping("/available-times")
    public Map<String, List<Map<String, Object>>> getAvailableTimes(@RequestParam String hospitalId, @RequestParam String date) {
        validateHospitalId(hospitalId);
        validateDate(date);
        return availableTimeService.getAvailableTimes(hospitalId, date);
    }

    private void validateHospitalId(String hospitalId) {
        if (!hospitalId.matches("\\d{3}-\\d{2}-\\d{5}")) {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "잘못된 형식(병원아이디 3-2-5)");
        }
    }

    private void validateDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "잘못된 형식(yyyy-MM-dd)");
        }
    }


}
