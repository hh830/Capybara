package com.codingrecipe.member;

import com.codingrecipe.member.entity.Hospital;
import com.codingrecipe.member.repository.likesRepository.LikesRepository;
import com.codingrecipe.member.service.hospitalService.LikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class LikeServiceTest {

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private LikeService likeService;

    @Test
    public void testGetTopHospitalsByLikesReturnsHospitalInfo() {
        List<Hospital> hospitals = likeService.getTopHospitalsByLikes();
        assertFalse(hospitals.isEmpty()); // 병원 목록이 비어있지 않은지 확인

        for (Hospital hospital : hospitals) {
            System.out.println(hospital.getName());

            assertNotNull(hospital.getName()); // 병원 이름이 null이 아닌지 확인
            assertNotNull(hospital.getAddress()); // 병원 주소가 null이 아닌지 확인
            // 기타 병원 정보 확인
        }
    }

}
