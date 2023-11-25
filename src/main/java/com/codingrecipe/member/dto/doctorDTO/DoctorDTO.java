package com.codingrecipe.member.dto.doctorDTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.attoparser.dom.Text;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DoctorDTO {
    private String name;
    private String biography;
    private String gender;

    public DoctorDTO(String name, String biography, String gender) {
        this.name = name;
        this.biography = biography;
        this.gender = gender;
    }
}
