package com.codingrecipe.member.service.hospitalService;

import com.codingrecipe.member.entity.Hospital;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.hospitalRepository.HospitalRepository;
import com.codingrecipe.member.repository.likesRepository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikesRepository likesRepository;

    @Autowired
    public LikeService(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

    public List<Hospital> getTopHospitalsByLikes() {
        try {

            Pageable topThree = PageRequest.of(0, 3);
            return likesRepository.findTopHospitalsByLikes(topThree).stream()
                    .map(result -> (Hospital) result[0])
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "잘못된 요청");
        }

    }
}
