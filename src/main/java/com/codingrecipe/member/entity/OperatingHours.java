package com.codingrecipe.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor // Lombok을 사용하여 기본 생성자를 추가
@Table(name = "operatinghours")
public class OperatingHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
/*
    @Column(name = "business_id")
    private String businessId;*/

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "opening_hours")
    private String openingHours;

    @Column(name = "break_time")
    private String breakTime;
    @ManyToOne
    @JoinColumn(name = "business_id")
    private Hospital hospital;

}
