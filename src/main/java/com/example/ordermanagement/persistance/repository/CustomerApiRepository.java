package com.example.ordermanagement.persistance.repository;

import com.example.ordermanagement.domain.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CustomerApiRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findBySeq(Long seq);

    Customer save(Customer customer);
}
