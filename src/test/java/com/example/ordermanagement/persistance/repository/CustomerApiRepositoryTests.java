package com.example.ordermanagement.persistance.repository;

import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.domain.model.enumClass.RoleStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@DataJpaTest
class CustomerApiRepositoryTests {

    @Autowired
    private CustomerApiRepository customerApiRepository;

    @Test
    @DisplayName("요청한 단일정보를 가져온다.")
    public void findById(){
        Customer customer = customerApiRepository.findBySeq(1L).orElseThrow(NoSuchElementException::new);

        Assertions.assertEquals("홍길동",customer.getName());
        Assertions.assertEquals("BRONZE",customer.getRole().toString());
        Assertions.assertEquals(1,customer.getRole().getId());
        Assertions.assertEquals("브론즈",customer.getRole().getTitle());
        Assertions.assertEquals("2010-02-13",customer.getBirthday().toString());
    }

    @Test
    @DisplayName("요청한 단일정보를 저장한다.")
    public void create(){
        Customer resource = Customer.builder()
                .id("aws1234")
                .password("aws1234")
                .name("aws동")
                .nickname("aws동이")
                .birthday(LocalDate.of(2012,05,23))
                .phoneNumber("010-0110-0220")
                .email("aws@naver.com")
                .address("서울 마포구")
                .role(RoleStatus.BRONZE)
                .grade(2)
                .registeredAt(LocalDate.of(2002,12,23))
                .build();

        Customer customer = customerApiRepository.save(resource);
        Customer findCustomer = customerApiRepository.findBySeq(3L).orElseThrow(RuntimeException::new);

        Assertions.assertEquals(customer,findCustomer);
    }
}