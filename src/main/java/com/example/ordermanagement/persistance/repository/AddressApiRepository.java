package com.example.ordermanagement.persistance.repository;

import com.example.ordermanagement.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressApiRepository extends JpaRepository<Address,Long> {
    Optional<Address> findBySeq(Long seq);
}
