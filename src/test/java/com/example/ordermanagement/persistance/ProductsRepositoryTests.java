package com.example.ordermanagement.persistance;

import com.example.ordermanagement.domain.Products;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class ProductsRepositoryTests {

    @Autowired
    private ProductsRepository productsRepository;

    @Test
    public void getProducts(){
        List<Products> products = productsRepository.getProducts();
        log.info("{}",products);
    }
}