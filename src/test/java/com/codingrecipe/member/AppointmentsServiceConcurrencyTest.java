package com.codingrecipe.member;

import com.codingrecipe.member.dto.appointmentsDTO.AppointmentsDTO;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.service.appointmentsService.AppointmentsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Transactional
@SpringBootTest
public class AppointmentsServiceConcurrencyTest {

    @Autowired
    private AppointmentsService appointmentsService;

    @Test
    public void testConcurrentReservation() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2); // 10개의 스레드를 사용하는 스레드 풀

        Runnable reservationTask = () -> {
            // 예약 요청 데이터 생성
            AppointmentsDTO appointmentsDTO = new AppointmentsDTO();
            appointmentsDTO.setDate(LocalDate.of(2024, 2, 9));
            appointmentsDTO.setTime(LocalTime.of(11, 0));
            appointmentsDTO.setHospitalId("074-04-27241");
            String userId = "Maroon0054"; // 테스트를 위한 사용자 ID

            // 예약 요청 실행
            try {
                ResponseEntity<?> response = appointmentsService.createReservation(appointmentsDTO, userId);
                System.out.println("예약 성공: " + response);
            } catch (CustomValidationException e) {
                System.out.println("예약 실패: " + e.getMessage());
            }
        };

        // 동시에 예약 요청 실행
        for (int i = 0; i < 2; i++) {
            executorService.submit(reservationTask);
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES); // 모든 작업이 완료될 때까지 최대 1분 동안 대기
    }
}
