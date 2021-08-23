package com.example.ordermanagement.domain.model.entity;

import com.example.ordermanagement.domain.model.enumClass.RoleStatus;
import com.example.ordermanagement.domain.model.enumClass.RoleStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String id;

    private String password;

    private String name;

    private String nickname;

    private LocalDate birthday;

    private String phoneNumber;

    private String email;

    private String address;

    @Convert(converter = RoleStatusConverter.class)
    private RoleStatus role;

    private int grade;

    private LocalDate registeredAt;
}
