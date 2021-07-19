package com.example.ordermanagement.presentation;

import com.example.ordermanagement.domain.Products;
import com.example.ordermanagement.persistance.ProductsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductsController {

    private final ProductsRepository productsRepository;

    public ProductsController(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @GetMapping("/products")
    public List<Products> findProducts(){
        return productsRepository.getUserList();
    }
}
