package com.example.ordermanagement.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String name;

    private int price;
}
