package com.codingrecipe.member;

import com.codingrecipe.member.exception.CustomValidationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    @ResponseBody
    public ResponseEntity<?> handleCustomValidationException(CustomValidationException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", e.getStatus());
        response.put("message", e.getMessage());

        // JSON 응답 반환
        return ResponseEntity
                .status(HttpStatus.valueOf(e.getStatus()))
                .body(response);
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(ChangeSetPersister.NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
