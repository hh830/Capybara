package com.codingrecipe.member.service.hospitalService;

import com.codingrecipe.member.dto.hospitalDTO.LikesDTO;
import com.codingrecipe.member.entity.Likes;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.likesRepository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class LikesCancelService {

    @Autowired
    private LikesRepository likesRepository;

    @Transactional
    public ResponseEntity<?> deleteLike(LikesDTO likesDTO, String userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean alreadyLiked = likesRepository.existsByHospital_BusinessIdAndPatients_PatientId(likesDTO.getHospitalId(), userId);

            if (alreadyLiked) { //좋아요를 이미 누른 경우
                Likes newLike = likesRepository.findByHospital_BusinessIdAndPatients_PatientId(likesDTO.getHospitalId(), userId);
                likesRepository.delete(newLike);
            } else{
                throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "잘못된 요청 (좋아요를 누르지 않음)");
            }

            long count = likesRepository.getLikesCountForHospital(likesDTO.getHospitalId());

            response.put("status", HttpStatus.OK);
            response.put("message", "좋아요 삭제 성공");
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
