package com.example.ordermanagement.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Products {
    private Long prodId;
    private String prodName;
    private Long prodPrice;
}
