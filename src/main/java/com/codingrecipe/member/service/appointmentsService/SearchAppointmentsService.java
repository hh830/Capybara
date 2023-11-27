package com.codingrecipe.member.service.appointmentsService;

import com.codingrecipe.member.dto.appointmentsDTO.AppointmentsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SearchAppointmentsService {

    public List<AppointmentsDTO> searchAppointments(String userId, String hospitalName, LocalDate date){
        if(hospitalName != null && date != null){ //캘린더 날짜랑 검색어 둘다 입력된 것

        }
        else if(date != null){ //날짜만 입력된 것

        }
        else{ //검색어만 입력된 것

        }

        return null;
    }
}
