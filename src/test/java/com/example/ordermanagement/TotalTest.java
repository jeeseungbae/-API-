package com.example.ordermanagement;

import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.domain.model.enumClass.GradeStatus;
import com.example.ordermanagement.presentation.apiController.CustomerApiController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TotalTest {

    private final CustomerApiController customerApiController;

    @Autowired
    public TotalTest(CustomerApiController customerApiController) {
        this.customerApiController = customerApiController;
    }

    @Test
    @Transactional
    public void create(){
        Customer resource = Customer.builder()
                .seq(1L)
                .userId("hdhrtjty")
                .password("wwef12345")
                .name("빼빼로")
                .nickname("빼빼로다크")
                .birthday(LocalDate.of(2012,05,23))
                .phoneNumber("010-4566-0220")
                .email("aws빼빼로@naver.com")
                .address("서울 마포구")
                .grade(GradeStatus.BRONZE)
                .role(2)
                .registeredAt(LocalDate.now())
                .build();
        customerApiController.create(resource);
    }
}
