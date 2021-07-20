package com.example.ordermanagement.persistance;

import com.example.ordermanagement.domain.Products;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Slf4j
public class ProductsRepositoryTests {

    @Mock
    private ProductsRepository productsRepository;

    @Test
    @DisplayName("모든 제품을 아이디 순서대로 불러온다.")
    public void getProducts(){
        List<Products> products = productsRepository.getProducts();
        int count=1;
        for(Products product : products){
            Assertions.assertEquals(product.getProdId(),count++);
            Assertions.assertEquals(product.getProdPrice(),any());
            Assertions.assertEquals(product.getProdName(),any());
        }
    }
}