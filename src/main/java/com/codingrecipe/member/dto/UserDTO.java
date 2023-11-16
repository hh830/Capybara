package com.codingrecipe.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {
    private String userId;
    private String userName;
    private String birthDate;
    private String phoneNumber;

    // 생성자, getter, setter 등을 포함
}
