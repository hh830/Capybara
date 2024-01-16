package com.codingrecipe.member.service.hospitalService;


import com.codingrecipe.member.dto.hospitalDTO.LikesDTO;
import com.codingrecipe.member.entity.Likes;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.hospitalRepository.HospitalRepository;
import com.codingrecipe.member.repository.likesRepository.LikesRepository;
import com.codingrecipe.member.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class LikesAddService {

    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Transactional
    public ResponseEntity<?> addLikeIfNotExists(LikesDTO likesDTO, String userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean alreadyLiked = likesRepository.existsByHospital_BusinessIdAndPatients_PatientId(likesDTO.getHospitalId(), userId);
            if (!alreadyLiked) {
                Likes newLike = new Likes();
                newLike.setHospitalId(likesDTO.getHospitalId(), hospitalRepository);
                newLike.setUserId(userId, patientRepository);
                likesRepository.save(newLike);
            } else{
                throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "잘못된 요청 (이미 좋아요를 누름)");
            }

            long count = likesRepository.getLikesCountForHospital(likesDTO.getHospitalId());

            response.put("status", HttpStatus.OK);
            response.put("message", "좋아요 등록 성공");
            response.put("hospitalId", likesDTO.getHospitalId());
            response.put("total", count);

            return ResponseEntity.status(HttpStatus.OK.value()).body(response);


        } catch (CustomValidationException e)
        {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);

        }
        catch (Exception e){
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);
        }

    }


}
