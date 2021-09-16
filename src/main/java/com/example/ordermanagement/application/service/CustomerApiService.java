package com.example.ordermanagement.application.service;

import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.domain.model.entity.CustomerDto;
import com.example.ordermanagement.exception.NoSuchDataException;
import com.example.ordermanagement.persistance.repository.CustomerApiRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


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

    @Transactional(readOnly = true)
    public List<Customer> findAll(){
        return customerApiRepository.findAll();
    }

    public Customer modify(CustomerDto resource){
        Customer customer = changeData(resource);
        return customerApiRepository.save(customer);
    }
    private Customer changeData(CustomerDto resource){
        Customer customer = findBySeq(resource.getSeq());
        return checkDataSame(customer,resource);
    }

    private Customer checkDataSame(Customer customer, CustomerDto resource){
        if(resource.getName().isEmpty()){
            resource.setName(customer.getName());
        }

        if(resource.getNickname().isEmpty()){
            resource.setNickname(customer.getNickname());
        }

        if(resource.getBirthday()==null){
            resource.setBirthday(customer.getBirthday());
        }

        if(resource.getEmail().isEmpty()){
            resource.setEmail(customer.getEmail());
        }

        if(resource.getAddress().isEmpty()){
            resource.setAddress(customer.getAddress());
        }

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

    public Customer create(Customer customer){
        Optional<Customer> resource = customerApiRepository.checkExists(customer);
        resource.ifPresent(select->{
            responseException(select,customer);
        });
        return customerApiRepository.save(customer);
    }

    private void responseException(Customer user, Customer customer){
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

    public void deleteBySeq(Long seq){
        customerApiRepository.deleteById(seq);
    }
}
