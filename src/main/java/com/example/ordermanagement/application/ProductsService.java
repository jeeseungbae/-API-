package com.example.ordermanagement.application;

import com.example.ordermanagement.domain.Products;
import com.example.ordermanagement.persistance.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Products> getProducts() {
        return productsRepository.getProducts();
    }
}
