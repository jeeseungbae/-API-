package com.example.ordermanagement.presentation;

import com.example.ordermanagement.application.ProductsService;
import com.example.ordermanagement.domain.Products;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("")
    public List<Products> getUserList(){
        return productsService.getProducts();
    }
}
