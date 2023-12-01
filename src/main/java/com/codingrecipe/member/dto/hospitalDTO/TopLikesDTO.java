package com.codingrecipe.member.dto.hospitalDTO;

import com.codingrecipe.member.entity.Hospital;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TopLikesDTO {

    private String id;
    private String name;
    private String address;
    private String department;

    public TopLikesDTO(String businessId, String name, String address, String department) {
        this.id = businessId;
        this.name = name;
        this.address = address;
        this.department = department;
    }
}
