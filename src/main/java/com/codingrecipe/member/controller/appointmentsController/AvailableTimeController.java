package com.codingrecipe.member.controller.appointmentsController;


import com.codingrecipe.member.service.appointmentsService.AvailableTimeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping("/reservations")
public class AvailableTimeController {
    @Autowired
    private AvailableTimeService availableTimeService;

    @GetMapping("/available-times")
    public Map<String, List<String>> getAvailableTimes(@RequestParam String hospitalId, @RequestParam String date) {
        return availableTimeService.getAvailableTimes(hospitalId, date);
    }
}
