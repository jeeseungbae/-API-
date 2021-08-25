package com.example.ordermanagement.persistance.repository;

import com.example.ordermanagement.domain.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerApiRepository extends JpaRepository<Customer,Long> {

    Customer save(Customer customer);

    Optional<Customer> findBySeq(Long seq);

    Optional<Customer> findByUserId(String id);

    Optional<Customer> findByNickname(String nickname);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
