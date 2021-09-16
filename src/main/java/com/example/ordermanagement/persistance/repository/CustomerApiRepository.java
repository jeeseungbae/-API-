package com.example.ordermanagement.persistance.repository;

import com.example.ordermanagement.domain.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerApiRepository extends JpaRepository<Customer,Long> {

    Customer save(Customer customer);

    Optional<Customer> findBySeq(Long seq);

    List<Customer> findAll();

    /**
     * exist 코드 작성 필요
     */
//    @Query(value = "SELECT c FROM Customer c where c.userId = :#{#customer.userId}")
//    Optional<Customer> checkExists(@Param("customer")Customer customer);

    @Query(value = "SELECT * FROM customer "+
            "WHERE user_id = :#{#customer.userId} "+
            "or nickname = :#{#customer.nickname} "+
            "or phone_number = :#{#customer.phoneNumber} "+
            "or email = :#{#customer.email} "+
            "limit 1 ",nativeQuery = true)
    Optional<Customer> checkExists(@Param("customer") Customer customer);
}
