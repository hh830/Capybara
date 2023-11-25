package com.codingrecipe.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.attoparser.dom.Text;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor // Lombok을 사용하여 기본 생성자를 추가
@Table(name = "doctors")
public class Doctors {
    @Id
    @Column(name = "license_number")
    private String licenseNumber;

    @Column
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String biography;

    @ManyToOne
    @JoinColumn(name = "business_id", referencedColumnName = "business_id")
    private Hospital hospital;


    @Column(name = "gender", length = 1)
    private String gender;

}
