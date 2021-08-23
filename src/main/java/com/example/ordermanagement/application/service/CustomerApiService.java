package com.example.ordermanagement.application.service;

import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.persistance.repository.CustomerApiRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CustomerApiService {

    private final CustomerApiRepository customerApiRepository;

    public CustomerApiService(CustomerApiRepository customerApiRepository) {
        this.customerApiRepository = customerApiRepository;
    }

    public Customer findBySeq(Long seq){
        return customerApiRepository.findBySeq(seq)
                .orElseThrow(()->new NoSuchElementException("데이터가 존재하지 않습니다."));
    }

    public Customer create(Customer customer){
        return customerApiRepository.save(customer);
    }

}
