package com.codingrecipe.member.dto.medicalRecordDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecordDTO {

    private int recordId;
    private LocalDate recordDate;
    private String doctorName;
    private String hospitalName;

    public RecordDTO(int recordId, LocalDate recordDate, String doctorName, String hospitalName) {
        this.recordId = recordId;
        this.recordDate = recordDate;
        this.doctorName = doctorName;
        this.hospitalName = hospitalName;
    }
}
