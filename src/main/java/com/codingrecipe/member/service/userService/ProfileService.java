package com.codingrecipe.member.service.userService;

import com.codingrecipe.member.StringUtils;
import com.codingrecipe.member.dto.userDTO.ProfileDTO;
import com.codingrecipe.member.entity.Patients;
import com.codingrecipe.member.exception.CustomServiceException;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.exception.NotFoundException;
import com.codingrecipe.member.repository.userRepository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.regex.Pattern;

@Service
public class ProfileService {
    private final PatientRepository patientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileService(PatientRepository patientRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.patientRepository = patientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public Patients updateProfile(String username, ProfileDTO profileDTO) {
        try {
            // 사용자 정보 조회
            Patients patients = patientRepository.findById(username)
                    .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

            StringUtils.removeSpacesFromDtoFields(profileDTO); //공백 제거

            // 비밀번호 변경 (null이 아닌 경우에만)
            if (profileDTO.getPassword() == null || profileDTO.getPassword().isEmpty()) {
            }
            else{
                if(profileDTO.getPassword().length() >= 8 && Pattern.matches("^[a-zA-Z0-9\\p{Punct}]+$", profileDTO.getPassword())){
                    patients.setPassword(passwordEncoder.encode(profileDTO.getPassword()));
                }else{
                    throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "비밀번호 형식 오류");
                }

            }

            // 이름 변경 (null이 아닌 경우에만)
            if (profileDTO.getUserName()==null || profileDTO.getUserName().isEmpty()) {
            }
            else{
                if (!isValidKoreanName(profileDTO.getUserName())) {
                    throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "이름 형식 오류 (한글만 포함)");
                } else {
                    patients.setName(profileDTO.getUserName());
                }
            }
// 전화번호 형식 검사

            // 전화번호 변경 (null이 아닌 경우에만)
            if (profileDTO.getPhoneNumber()== null || profileDTO.getPhoneNumber().isEmpty()) {

            }else{
                if(Pattern.matches("\\d{3}-\\d{4}-\\d{4}", profileDTO.getPhoneNumber()))
                {
                    patients.setPhoneNumber(profileDTO.getPhoneNumber());

                } else{
                    throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "전화번호 형식 오류 (000-0000-0000)");
                }
            }

            return patientRepository.save(patients);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new CustomValidationException(HttpStatus.CONFLICT.value(), "동시 업데이트로 인한 예약 실패, 다시 실행해주세요.");
        } catch (DataAccessException e) {
            throw new CustomServiceException("서버 오류", e);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("사용자를 찾을 수 없습니다.", e);
        }
    }

    private boolean isValidKoreanName(String name) {
        return name != null && Pattern.matches("^[가-힣]+$", name);
    }


}
