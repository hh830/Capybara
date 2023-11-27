package com.codingrecipe.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor // Lombok을 사용하여 기본 생성자를 추가
@Table(name = "hospital")
public class Hospital {
        @Id
        @Column(name = "business_id")
        private String businessId;
        @Column
        private String name;

        @Column
        private String department;

        @Column
        private String address;

        @Column(name = "phone_number")
        private String phoneNumber;

        @Column(name = "open_date")
        private String openDate;

        // Likes와의 관계 (예: OneToMany, ManyToOne 등)
        @OneToMany(mappedBy = "hospital")
        private Set<Likes> likes;

        // OperatingHours와의 관계
        @OneToMany(mappedBy = "hospital")
        private Set<OperatingHours> operatingHours;

        @OneToMany(mappedBy = "hospital")
        private Set<Doctors> doctors;

        @OneToMany(mappedBy = "hospital")
        private Set<Appointments> appointments;
}
