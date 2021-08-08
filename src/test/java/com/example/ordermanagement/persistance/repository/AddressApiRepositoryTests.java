package com.example.ordermanagement.persistance.repository;

import com.example.ordermanagement.domain.model.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@DataJpaTest // 자동적으로 롤백
@DisplayName("AddressApiRepositoryTests")
public class AddressApiRepositoryTests {

    @Autowired
    private AddressApiRepository addressApiRepository;

    @Nested
    @DisplayName("주어진 정보를 생성한다.")
    public class createdData{

        @Test
        @DisplayName("성공")
        public void create(){
            Address resource = Address.builder()
                    .content("서울 중랑구")
                    .distinction(1)
                    .build();

            Address address = addressApiRepository.save(resource);

            Assertions.assertEquals(address.getSeq(),5L);
            Assertions.assertEquals(address.getContent(),resource.getContent());
            Assertions.assertEquals(address.getDistinction(),resource.getDistinction());
        }
    }

    @Nested
    @DisplayName("단일 정보를 조회한다.")
    public class FindData{

        @DisplayName("성공")
        @Test
        public void findById(){
            Optional<Address> address = addressApiRepository.findBySeq(1L);
            Assertions.assertTrue(address.isPresent());

            address.ifPresent(select->{
                Assertions.assertEquals(select.getSeq(),1L);
                Assertions.assertEquals(select.getContent(),"서울 중랑구");
                Assertions.assertEquals(select.getDistinction(),1);
            });
        }

        @DisplayName("실패 - 데이터 없음")
        @Test
        public void notFindById(){
            Optional<Address> address = addressApiRepository.findBySeq(10L);
            Assertions.assertFalse(address.isPresent());
        }
    }

}