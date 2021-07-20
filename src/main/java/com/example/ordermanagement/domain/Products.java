package com.example.ordermanagement.domain;

import lombok.*;


@Getter
@Setter
@Data
public class Products {
    private Long prodId;
    private String prodName;
    private Long prodPrice;
}
