package com.example.ordermanagement.application.service;

import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.domain.model.enumClass.RoleStatus;
import com.example.ordermanagement.persistance.repository.CustomerApiRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


class CustomerApiServiceTests {

    private CustomerApiService customerApiService;

    @Mock
    private CustomerApiRepository customerApiRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        createData();
        customerApiService = new CustomerApiService(customerApiRepository);
    }

    private void createData(){
        Customer customer = Customer.builder()
                .seq(1L)
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

        given(customerApiRepository.findBySeq(1L)).willReturn(java.util.Optional.ofNullable(customer));
        given(customerApiRepository.save(any())).willReturn(customer);
    }

    @Nested
    @DisplayName("정보를 조회한다.")
    public class Find{

        @Test
        @DisplayName("성공 : 단일 정보를 조회한다.")
        public void findBySeq(){
            Customer customer = customerApiService.findBySeq(1L);
            Assertions.assertEquals("aws1234",customer.getId());
            Assertions.assertEquals("BRONZE",customer.getRole().toString());
            Assertions.assertEquals(LocalDate.of(2002,12,23),customer.getRegisteredAt());
            verify(customerApiRepository).findBySeq(1L);
        }
    }

    @Nested
    @DisplayName("정보를 저장한다.")
    public class Save{

        @Test
        @DisplayName("성공 : 단일 정보를 저장한다.")
        public void create(){
            Customer resource = Customer.builder()
                    .seq(1L)
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

            Customer customer = customerApiService.create(resource);

            Assertions.assertEquals("aws1234",customer.getId());
            Assertions.assertEquals("BRONZE",customer.getRole().toString());
            Assertions.assertEquals(LocalDate.of(2002,12,23),customer.getRegisteredAt());

            verify(customerApiRepository).save(any());
        }
    }

}