package com.codingrecipe.member;

import com.codingrecipe.member.entity.Appointments;
import com.codingrecipe.member.repository.appointmentsRepository.AppointmentsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class AppointmentsRepositoryTest {

    @Autowired
    private AppointmentsRepository appointmentsRepository;

    @Test
    @Transactional
    public void testCountByHospital_BusinessIdAndAppointmentDateAndAppointmentTime() {
        // 테스트에 사용할 매개변수를 설정합니다.
        String testBusinessId = "003-92-12348"; // 실제 데이터베이스에 존재하는 값을 사용하세요.
        LocalDate testDate = LocalDate.of(2024, 01, 24); // 적절한 날짜로 설정하세요.
        LocalTime testTime = LocalTime.of(16, 0); // 적절한 시간으로 설정하세요.

        // 메소드를 호출하여 결과를 받습니다.
        int count = appointmentsRepository.countByHospital_BusinessIdAndAppointmentDateAndAppointmentTime(
                testBusinessId, testDate, testTime);

        // 결과를 확인합니다.
        System.out.println("Count: " + count);

        // 기대하는 결과값으로 확인합니다. 이 값은 실제 데이터에 따라 달라집니다.
        assertEquals(1, count);
    }
}
