package com.example.ordermanagement.application.service;

import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.exception.NoSuchDataException;
import com.example.ordermanagement.persistance.repository.CustomerApiRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public List<Customer> findAll(){
        return customerApiRepository.findAll();
    }

    public Customer create(Customer customer){
        duplicateCheckUserId(customer);
        return customerApiRepository.save(customer);
    }

    private void duplicateCheckUserId(Customer customer){
        if(customerApiRepository.findByUserId(customer.getUserId()).isPresent()){
            throw new DuplicateKeyException("이미 존재하는 아이디입니다.");
        }
        duplicateCheckNickname(customer);
    }
    private void duplicateCheckNickname(Customer customer){
        if(customerApiRepository.findByNickname(customer.getNickname()).isPresent()){
            throw new DuplicateKeyException("이미 존재하는 닉네임입니다.");
        }
        duplicateCheckPhoneNumber(customer);
    }
    private void duplicateCheckPhoneNumber(Customer customer){
        if(customerApiRepository.findByPhoneNumber(customer.getPhoneNumber()).isPresent()){
            throw new DuplicateKeyException("이미 존재하는 전화번호입니다.");
        }
        duplicateCheckEmail(customer);
    }
    private void duplicateCheckEmail(Customer customer){
        if(customerApiRepository.findByEmail(customer.getEmail()).isPresent()){
            throw new DuplicateKeyException("이미 존재하는 이메일입니다.");
        }
    }
}
