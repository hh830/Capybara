package com.codingrecipe.member.controller.hospitalController;

import com.codingrecipe.member.dto.hospitalDTO.TopLikesDTO;
import com.codingrecipe.member.entity.Hospital;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.service.hospitalService.HospitalService;
import com.codingrecipe.member.service.hospitalService.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hospitals")
public class TopLikesController {

    @Autowired
    private LikeService likeService;



    @GetMapping("/top")
    public ResponseEntity<?> getTopHospitals() {
        try {
            List<Hospital> hospitals = likeService.getTopHospitalsByLikes();
            List<TopLikesDTO> hospitalSummaries = new ArrayList<>();

            for (Hospital hospital : hospitals) {
                TopLikesDTO dto = new TopLikesDTO(hospital.getBusinessId(), hospital.getName(), hospital.getAddress(), hospital.getDepartment());
                hospitalSummaries.add(dto);
            }
            return ResponseEntity.ok(hospitalSummaries);
        } catch (CustomValidationException e){
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "잘못된 요청");
        } catch (Exception e){
            throw new CustomValidationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "조회 오류");
        }
    }
}
