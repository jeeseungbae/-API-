package com.example.ordermanagement.application.service;

import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.domain.model.entity.CustomerDto;
import com.example.ordermanagement.exception.NoSuchDataException;
import com.example.ordermanagement.persistance.repository.CustomerApiRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;


@Service
public class CustomerApiService {

    private final CustomerApiRepository customerApiRepository;

    public CustomerApiService(CustomerApiRepository customerApiRepository) {
        this.customerApiRepository = customerApiRepository;
    }

    @Transactional(readOnly = true)
    public Customer findBySeq(Long seq){
        return customerApiRepository.findBySeq(seq)
                .orElseThrow(()->new NoSuchDataException(seq));
    }

    public List<Customer> findAll(){
        return customerApiRepository.findAll();
    }

    public Customer modify(CustomerDto resource){
        Customer customer = changeData(resource);
        return customerApiRepository.save(customer);
    }

    public Customer create(Customer customer){
        List<Customer> customers = customerApiRepository.findAll();
        duplicateCheckCustomer(customer,customers.iterator());
        return customerApiRepository.save(customer);
    }

    public void deleteBySeq(Long seq){
        Customer customer = findBySeq(seq);
        customerApiRepository.delete(customer);
    }

    private Customer changeData(CustomerDto resource){
        Customer customer = findBySeq(resource.getSeq());
        return changeName(customer,resource);
    }

    private Customer changeName(Customer customer, CustomerDto resource){
        if(resource.getName().isEmpty()){
            resource.setName(customer.getName());
        }
        return changeNickName(customer,resource);
    }
    private Customer changeNickName(Customer customer, CustomerDto resource){
        if(resource.getNickname().isEmpty()){
            resource.setNickname(customer.getNickname());
        }
        return changeBirthday(customer,resource);
    }
    private Customer changeBirthday(Customer customer, CustomerDto resource){
        if(resource.getBirthday()==null){
            resource.setBirthday(customer.getBirthday());
        }
        return changeEmail(customer,resource);
    }
    private Customer changeEmail(Customer customer, CustomerDto resource){
        if(resource.getEmail().isEmpty()){
            resource.setEmail(customer.getEmail());
        }
        return changeAddress(customer,resource);
    }
    private Customer changeAddress(Customer customer, CustomerDto resource){
        if(resource.getAddress().isEmpty()){
            resource.setAddress(customer.getAddress());
        }
        return changePhoneNumber(customer,resource);
    }
    private Customer changePhoneNumber(Customer customer, CustomerDto resource){
        if(resource.getPhoneNumber().isEmpty()){
            resource.setPhoneNumber(customer.getPhoneNumber());
        }
        return changeCustomer(customer,resource);
    }
    private Customer changeCustomer(Customer customer, CustomerDto resource){
        return Customer.builder()
                .seq(customer.getSeq())
                .userId(customer.getUserId())
                .password(customer.getPassword())
                .name(resource.getName())
                .nickname(resource.getNickname())
                .birthday(resource.getBirthday())
                .phoneNumber(resource.getPhoneNumber())
                .email(resource.getEmail())
                .address(resource.getAddress())
                .registeredAt(customer.getRegisteredAt())
                .role(customer.getRole())
                .grade(customer.getGrade())
                .build();
    }

    private void duplicateCheckCustomer(Customer customer, Iterator<Customer> customers){
        while(customers.hasNext()){
            Customer user = customers.next();
            if(user.getUserId().equals(customer.getUserId())){
                throw new DuplicateKeyException("이미 존재하는 아이디 입니다.");
            }
            if(user.getNickname().equals(customer.getNickname())){
                throw new DuplicateKeyException("이미 존재하는 닉네임입니다.");
            }
            if(user.getPhoneNumber().equals(customer.getPhoneNumber())){
                throw new DuplicateKeyException("이미 존재하는 전화번호입니다.");
            }
            if(user.getEmail().equals(customer.getEmail())){
                throw new DuplicateKeyException("이미 존재하는 이메일입니다.");
            }
        }
    }
}
