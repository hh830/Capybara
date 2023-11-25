package com.codingrecipe.member.dto.appointmentsDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AvailableTimeDTO {
    private String availableDate;
    private String availableTime;

    public AvailableTimeDTO(String availableDate, String availableTime) {
        this.availableDate = availableDate;
        this.availableTime = availableTime;
    }
}
