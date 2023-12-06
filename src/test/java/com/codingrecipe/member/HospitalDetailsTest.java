package com.codingrecipe.member;


import com.codingrecipe.member.repository.operationTimeRepository.OperationTimeRepository;
import com.codingrecipe.member.service.hospitalService.HospitalDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HospitalDetailsTest {

    @Autowired
    private HospitalDetailsService hospitalDetailsService;

    @Mock
    private OperationTimeRepository operationTimeRepository;

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public HospitalDetailsTest() {
        MockitoAnnotations.initMocks(this);
    }

    private void mockCurrentTime(String time) {
        LocalTime mockedCurrentTime = LocalTime.parse(time, timeFormatter);
        // Inject the mocked time into your service, or override the method that returns the current time
        // Example: when(hospitalDetailsService.getCurrentTime()).thenReturn(mockedCurrentTime);
    }

    private void mockHospitalData(String operatingHours, String breakTime) {
        when(operationTimeRepository.findOperatingHoursByHospitalIdAndDay(any(), any())).thenReturn(operatingHours);
        when(operationTimeRepository.findBreakTimeByHospitalIdAndDay(any(), any())).thenReturn(breakTime);
    }

    @Test
    public void testGetHospitalDetails_Open() {
        //mockCurrentTime("10:00"); 21시까지 영업
        mockHospitalData("08:30~21:00", "12:00~13:00");

        String status = hospitalDetailsService.getHospitalDetails(null, "524-33-41451").getStatus();
        assertEquals("영업중", status);
    }

    @Test
    public void testGetHospitalDetails_Closed() {
        mockCurrentTime("10:00");
        mockHospitalData(null, null);

        String status = hospitalDetailsService.getHospitalDetails(null, "004-42-43839").getStatus();
        assertEquals("오늘휴무", status);
    }

    @Test
    public void testGetHospitalDetails_BreakTime() {
        mockCurrentTime("12:30");
        mockHospitalData("09:00~17:00", "12:00~13:00");

        String status = hospitalDetailsService.getHospitalDetails(null, "000-01-01945").getStatus();
        assertEquals("휴게시간", status);
    }

    @Test
    public void testGetHospitalDetails_BeforeOpening() {
        mockCurrentTime("08:00");
        mockHospitalData("09:00~17:00", "12:00~13:00");

        String status = hospitalDetailsService.getHospitalDetails(null, "000-01-01945").getStatus();
        assertEquals("영업전", status);
    }

    @Test
    public void testGetHospitalDetails_AfterClosing() {
        mockCurrentTime("18:00");
        mockHospitalData("09:00~17:00", "12:00~13:00");

        String status = hospitalDetailsService.getHospitalDetails(null, "000-01-01945").getStatus();
        assertEquals("영업종료", status);
    }

    // ... [Other necessary methods and setups]
}
