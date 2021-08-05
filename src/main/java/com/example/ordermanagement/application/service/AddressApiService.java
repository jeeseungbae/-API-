package com.example.ordermanagement.application.service;

import com.example.ordermanagement.domain.model.Address;
import com.example.ordermanagement.persistance.repository.AddressApiRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AddressApiService {

    private final AddressApiRepository addressApiRepository;

    public AddressApiService(AddressApiRepository addressApiRepository) {
        this.addressApiRepository = addressApiRepository;
    }

    public Address findBySeq(Long seq){
        return addressApiRepository.findBySeq(seq).orElseThrow(
                ()->new NoSuchElementException("값이 존재하지 않는 번호입니다."));
    }
}
