package com.codingrecipe.member;

import com.codingrecipe.member.entity.Doctors;
import com.codingrecipe.member.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DoctorRepositoryTest {




    @Autowired
    private DoctorRepository doctorRepository;

    @BeforeEach
    void setup() {
        // 테스트 데이터 삽입 로직

    }

    @Test
    void findByHospitalTest() {
        // 테스트 데이터 설정
        String businessId = "351-45-34766";

        // 메서드 실행
        List<Doctors> doctors = doctorRepository.findByHospital_BusinessId(businessId);

        // 결과 검증
        assertNotNull(doctors);
        assertFalse(doctors.isEmpty());
        // 추가적인 검증 로직
    }
}
