package com.example.ordermanagement.persistance.repository;

import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.domain.model.enumClass.GradeStatus;
import com.example.ordermanagement.exception.NoSuchDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class CustomerApiRepositoryTests {

    @Autowired
    private CustomerApiRepository customerApiRepository;

    @Test
    @DisplayName("요청한 단일정보를 가져온다.")
    public void findBySeq(){
        Customer customer = customerApiRepository.findBySeq(1L).orElseThrow(()-> new NoSuchDataException(1L));

        Assertions.assertEquals("홍길동",customer.getName());
        Assertions.assertEquals("BRONZE",customer.getGrade().toString());
        Assertions.assertEquals(1,customer.getGrade().getGradeId());
        Assertions.assertEquals("브론즈",customer.getGrade().getTitle());
        Assertions.assertEquals("2010-02-13",customer.getBirthday().toString());
    }

    @Test
    @DisplayName("요청한 모든 정보를 가져온다.")
    public void findAll(){
        List<Customer> customers = customerApiRepository.findAll();

        Assertions.assertEquals(1,customers.get(0).getSeq());
        Assertions.assertEquals("홍길동",customers.get(0).getName());
        Assertions.assertEquals(2,customers.get(1).getSeq());
        Assertions.assertEquals("둘리",customers.get(1).getName());
    }

    @Test
    @DisplayName("요청한 단일정보를 저장한다.")
    public void create(){
        Customer resource = Customer.builder()
                .userId("aws1234")
                .password("aws123456")
                .name("aws동")
                .nickname("aws동이")
                .birthday(LocalDate.of(2012,05,23))
                .phoneNumber("010-0110-0220")
                .email("aws@naver.com")
                .address("서울 마포구")
                .grade(GradeStatus.BRONZE)
                .role(2)
                .build();

        Customer customer = customerApiRepository.save(resource);
        Customer findCustomer = customerApiRepository.findBySeq(3L).orElseThrow(()->new NoSuchDataException(3L));

        Assertions.assertEquals(customer,findCustomer);
    }

    @Test
    @DisplayName("요청한 정보를 수정한다.")
    public void modifySuccess(){
        Customer resource = customerApiRepository.findBySeq(1L).orElseThrow(()-> new NoSuchDataException(1L));
        Customer createCustomer = Customer.builder()
                .seq(resource.getSeq())
                .userId(resource.getUserId())
                .password(resource.getPassword())
                .name("ssdfdsf")
                .nickname("wegwegwerg")
                .birthday(LocalDate.of(2014,12,23))
                .phoneNumber("010-2849-0000")
                .email("sfewsf@sjdkflsejf.com")
                .address("sfejsklejfklsefa")
                .registeredAt(resource.getRegisteredAt())
                .role(resource.getRole())
                .grade(resource.getGrade())
                .build();

        Assertions.assertNotEquals(createCustomer.getName(),resource.getName());
        Assertions.assertNotEquals(createCustomer.getNickname(),resource.getNickname());
        Assertions.assertNotEquals(createCustomer.getEmail(),resource.getEmail());
        Assertions.assertNotEquals(createCustomer.getAddress(),resource.getAddress());
        Assertions.assertNotEquals(createCustomer.getPhoneNumber(),resource.getPhoneNumber());

        Customer customerStore = customerApiRepository.save(createCustomer);

        Assertions.assertEquals(createCustomer.getName(),customerStore.getName());
        Assertions.assertEquals(createCustomer.getNickname(),customerStore.getNickname());
        Assertions.assertEquals(createCustomer.getEmail(),customerStore.getEmail());
        Assertions.assertEquals(createCustomer.getAddress(),customerStore.getAddress());
        Assertions.assertEquals(createCustomer.getPhoneNumber(),customerStore.getPhoneNumber());
    }
    @Test
    @DisplayName("요청한 정보를 삭제한다.")
    public void deleteSuccess(){
        Optional<Customer> customer = customerApiRepository.findBySeq(1L);
        Assertions.assertTrue(customer.isPresent());
        customer.ifPresent(select->{
            customerApiRepository.delete(select);
        });
        Optional<Customer> deleteCustomer = customerApiRepository.findBySeq(1L);
        Assertions.assertFalse(deleteCustomer.isPresent());
    }
}