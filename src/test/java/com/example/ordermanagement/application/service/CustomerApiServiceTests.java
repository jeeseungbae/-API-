package com.example.ordermanagement.application.service;

import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.domain.model.enumClass.GradeStatus;
import com.example.ordermanagement.persistance.repository.CustomerApiRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerApiServiceTests {

    @InjectMocks
    private CustomerApiService customerApiService;

    @Mock
    private CustomerApiRepository customerApiRepository;

    private static Customer resource;

    @BeforeAll
    static void createCustomer(){
        resource = Customer.builder()
                .seq(1L)
                .userId("aws1234")
                .password("aws1234")
                .name("aws동")
                .nickname("aws동이")
                .birthday(LocalDate.of(2012,05,23))
                .phoneNumber("010-0110-0220")
                .email("aws@naver.com")
                .address("서울 마포구")
                .grade(GradeStatus.BRONZE)
                .role(2)
                .registeredAt(LocalDate.of(2002,12,23))
                .build();
    }

    @Nested
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @DisplayName("정보를 조회한다.")
    public class Find{

        @Test
        @DisplayName("성공 : 단일 정보를 조회한다.")
        public void findBySeq(){
            given(customerApiRepository.findBySeq(1L)).willReturn(java.util.Optional.ofNullable(resource));

            Customer customer = customerApiService.findBySeq(1L);
            Assertions.assertEquals("aws1234",customer.getUserId());
            Assertions.assertEquals("BRONZE",customer.getGrade().toString());
            Assertions.assertEquals(LocalDate.of(2002,12,23),customer.getRegisteredAt());
            verify(customerApiRepository).findBySeq(1L);
        }

        @Test
        @DisplayName("실패 : 잘못된 정보로 Exception을 반환")
        public void errorFindBySeq(){
            given(customerApiRepository.findBySeq(3L)).willThrow(new NoSuchElementException(""));

            Assertions.assertThrows(NoSuchElementException.class,
                    ()->customerApiService.findBySeq(3L));

            verify(customerApiRepository,times(1)).findBySeq(3L);
        }
    }

    @Nested
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @DisplayName("정보를 저장한다.")
    public class Save{
        @Test
        @DisplayName("성공 : 단일 정보를 저장한다.")
        public void create(){
            given(customerApiRepository.save(any())).willReturn(resource);

            Customer customer = customerApiService.create(resource);

            Assertions.assertEquals("aws1234",customer.getUserId());
            Assertions.assertEquals("BRONZE",customer.getGrade().toString());
            Assertions.assertEquals(LocalDate.of(2002,12,23),customer.getRegisteredAt());

            verify(customerApiRepository).save(any());
        }

        @Nested
        @DisplayName("Error : 중복으로 인한 에러 반환")
        public class errorDuplicate {

            @Test
            @DisplayName("error : 아이디 중복")
            public void errorDuplicateUserId () {
                given(customerApiRepository.findByUserId(resource.getUserId())).willThrow(new DuplicateKeyException(""));

                Assertions.assertThrows(DuplicateKeyException.class,
                        () -> customerApiService.create(resource));
            }

            @Test
            @DisplayName("error : 닉네임 중복")
            public void errorDuplicateNickname () {
                given(customerApiRepository.findByNickname(resource.getNickname())).willThrow(new DuplicateKeyException(""));

                Assertions.assertThrows(DuplicateKeyException.class,
                        () -> customerApiService.create(resource));
            }
            @Test
            @DisplayName("error : 전화번호 중복")
            public void errorDuplicatePhoneNumber () {
                given(customerApiRepository.findByPhoneNumber(resource.getPhoneNumber())).willThrow(new DuplicateKeyException(""));

                Assertions.assertThrows(DuplicateKeyException.class,
                        () -> customerApiService.create(resource));
            }

            @Test
            @DisplayName("error : 이메일 중복")
            public void errorDuplicateEmail() {
                given(customerApiRepository.findByEmail(resource.getEmail())).willThrow(new DuplicateKeyException(""));

                Assertions.assertThrows(DuplicateKeyException.class,
                        () -> customerApiService.create(resource));
            }
        }
    }
}