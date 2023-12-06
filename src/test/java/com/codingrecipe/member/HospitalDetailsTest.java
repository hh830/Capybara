//package com.codingrecipe.member;
//
//
//import com.codingrecipe.member.repository.operationTimeRepository.OperationTimeRepository;
//import com.codingrecipe.member.service.hospitalService.HospitalDetailsService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//// ... [Other necessary imports]
//
//@SpringBootTest
//public class HospitalDetailsTest {
//
//    // ... [Mocks and setup]
//
//    @Autowired
//    private HospitalDetailsService hospitalDetailsService;
//    @Autowired
//    private OperationTimeRepository operationTimeRepository;
//    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//
//    @Test
//    public void testGetHospitalDetails_Open() {
//        // Assume current time is within operating hours but not break time
//        mockCurrentTime("10:00");
//        mockHospitalData("09:00~17:00", "12:00~13:00");
//
//        String status = hospitalDetailsService.getHospitalDetails("userId", "hospitalId").getStatus();
//        assertEquals("영업중", status);
//    }
//
//    @Test
//    public void testGetHospitalDetails_Closed() {
//        // Assume today is a day the hospital is closed
//        mockCurrentTime("10:00");
//        mockHospitalData(null, null);
//
//        String status = hospitalDetailsService.getHospitalDetails("userId", "hospitalId").getStatus();
//        assertEquals("오늘 휴무", status);
//    }
//
//    @Test
//    public void testGetHospitalDetails_BreakTime() {
//        // Assume current time is within break time
//        mockCurrentTime("12:30");
//        mockHospitalData("09:00~17:00", "12:00~13:00");
//
//        String status = hospitalDetailsService.getHospitalDetails("userId", "hospitalId").getStatus();
//        assertEquals("휴게시간", status);
//    }
//
//    @Test
//    public void testGetHospitalDetails_BeforeOpening() {
//        // Assume current time is before opening
//        mockCurrentTime("08:00");
//        mockHospitalData("09:00~17:00", "12:00~13:00");
//
//        String status = hospitalDetailsService.getHospitalDetails("userId", "hospitalId").getStatus();
//        assertEquals("영업전", status);
//    }
//
//    @Test
//    public void testGetHospitalDetails_AfterClosing() {
//        // Assume current time is after closing
//        mockCurrentTime("18:00");
//        mockHospitalData("09:00~17:00", "12:00~13:00");
//
//        String status = hospitalDetailsService.getHospitalDetails("userId", "hospitalId").getStatus();
//        assertEquals("영업종료", status);
//    }
//
//    private void mockCurrentTime(String time) {
//        // Implement logic to mock or inject current time in your service
//        LocalTime mockedCurrentTime = LocalTime.parse(time, timeFormatter);
//        // Set the mocked time in your service or context
//    }
//
//    private void mockHospitalData(String operatingHours, String breakTime) {
//        // Mock responses from operationTimeRepository and other necessary repositories
//        when(operationTimeRepository.findOperatingHoursByHospitalIdAndDay(anyString(), anyString())).thenReturn(operatingHours);
//        when(operationTimeRepository.findBreakTimeByHospitalIdAndDay(anyString(), anyString())).thenReturn(breakTime);
//
//        // Mock other repository responses as necessary
//    }
//
//    // ... [Other necessary methods and setups]
//}
