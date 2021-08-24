package com.example.ordermanagement.application.service;

import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.exception.NoSuchDataException;
import com.example.ordermanagement.persistance.repository.CustomerApiRepository;
import org.springframework.stereotype.Service;


@Service
public class CustomerApiService {

    private final CustomerApiRepository customerApiRepository;

    public CustomerApiService(CustomerApiRepository customerApiRepository) {
        this.customerApiRepository = customerApiRepository;
    }

    public Customer findBySeq(Long seq){
        return customerApiRepository.findBySeq(seq)
                .orElseThrow(()->new NoSuchDataException(seq));
    }

    public Customer create(Customer customer){
        return customerApiRepository.save(customer);
    }

}
