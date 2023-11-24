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
    private Text biography;

    @Column(name = "business_id")
    private String businessId;

    @Column
    private char genter;



}
