package com.codingrecipe.member.dto.userDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {
    //private String userId;
    private String userName;
    private String birthDate;
    private String phoneNumber;

    // 생성자, getter, setter 등을 포함
    public UserDTO(String username, String phoneNumber, String birthDate){
        this.userName = username;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }
}
