package com.example.ordermanagement.persistance.repository;


import com.example.ordermanagement.domain.model.Address;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest // 자동적으로 롤백
public class AddressApiRepositoryTests {

    @Autowired
    private AddressApiRepository addressApiRepository;

    @DisplayName("성공적으로 조회한 정보를 가져온다.")
    @Test
    public void findById(){
        Optional<Address> address = addressApiRepository.findBySeq(1L);
        Assertions.assertTrue(address.isPresent());

        address.ifPresent(select->{
            assertThat(select.getSeq(),is(1L));
            assertThat(select.getContent(),is("서울 중랑구"));
            assertThat(select.getDistinction(),is(1));
        });
    }

    @DisplayName("잘못된 값으로 정보를 불러오지 않는다.")
    @Test
    public void notFindById(){
        Optional<Address> address = addressApiRepository.findBySeq(10L);
        Assertions.assertFalse(address.isPresent());
    }
}