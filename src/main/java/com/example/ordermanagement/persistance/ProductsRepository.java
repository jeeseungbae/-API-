package com.example.ordermanagement.persistance;

import com.example.ordermanagement.domain.Products;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductsRepository {
    List<Products> getUserList();
}
