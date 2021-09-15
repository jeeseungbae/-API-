package com.example.ordermanagement.application.service;

import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.domain.model.entity.CustomerDto;
import com.example.ordermanagement.domain.model.enumClass.GradeStatus;
import com.example.ordermanagement.exception.NoSuchDataException;
import com.example.ordermanagement.persistance.repository.CustomerApiRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
                List<Customer> customers = new ArrayList<>();
                customers.add(resource);
                given(customerApiRepository.findAll()).willReturn(customers);

                Assertions.assertThrows(DuplicateKeyException.class,
                        () -> customerApiService.create(resource));
            }

            @Test
            @DisplayName("error : 닉네임 중복")
            public void errorDuplicateNickname () {
                List<Customer> customers = new ArrayList<>();
                customers.add(resource);
                given(customerApiRepository.findAll()).willReturn(customers);

                Assertions.assertThrows(DuplicateKeyException.class,
                        () -> customerApiService.create(resource));
            }
            @Test
            @DisplayName("error : 전화번호 중복")
            public void errorDuplicatePhoneNumber () {
                List<Customer> customers = new ArrayList<>();
                customers.add(resource);
                given(customerApiRepository.findAll()).willReturn(customers);

                Assertions.assertThrows(DuplicateKeyException.class,
                        () -> customerApiService.create(resource));
            }

            @Test
            @DisplayName("error : 이메일 중복")
            public void errorDuplicateEmail() {
                List<Customer> customers = new ArrayList<>();
                customers.add(resource);
                given(customerApiRepository.findAll()).willReturn(customers);

                Assertions.assertThrows(DuplicateKeyException.class,
                        () -> customerApiService.create(resource));
            }
        }
    }

    @Nested
    @DisplayName("정보를 수정한다.")
    public class Modify{

        @Test
        @DisplayName("성공 : 단일 정보를 수정")
        public void modifySuccess(){
            given(customerApiRepository.findBySeq(1L)).willReturn(java.util.Optional.ofNullable(resource));
            CustomerDto customerDto = CustomerDto.builder()
                    .seq(1L)
                    .name("swer123")
                    .nickname("afewh24")
                    .birthday(LocalDate.of(2000,12,23))
                    .email("sdgaweigj@naver.com")
                    .address("sdjflkewf")
                    .phoneNumber("010-0232-2324")
                    .build();

            Customer createCustomer = Customer.builder()
                    .seq(1L)
                    .userId(resource.getUserId())
                    .password(resource.getPassword())
                    .name(customerDto.getName())
                    .nickname(customerDto.getNickname())
                    .birthday(LocalDate.of(2000,12,23))
                    .email(customerDto.getEmail())
                    .address(customerDto.getAddress())
                    .phoneNumber(customerDto.getPhoneNumber())
                    .role(resource.getRole())
                    .grade(resource.getGrade())
                    .registeredAt(resource.getRegisteredAt())
                    .build();

            given(customerApiRepository.save(any())).willReturn(createCustomer);
            Customer customer = customerApiService.modify(customerDto);

            Assertions.assertEquals(customer.getName(),customerDto.getName());
            Assertions.assertEquals(customer.getNickname(),customerDto.getNickname());
            Assertions.assertEquals(customer.getEmail(),customerDto.getEmail());
            Assertions.assertEquals(customer.getAddress(),customerDto.getAddress());
            Assertions.assertEquals(customer.getPhoneNumber(),customerDto.getPhoneNumber());

            verify(customerApiRepository).save(any());
        }

        @Test
        @DisplayName("성공 : 일부 정보가 없으면 기존에 값을 대입하여 수정")
        public void failModifyInput(){
            given(customerApiRepository.findBySeq(1L)).willReturn(java.util.Optional.ofNullable(resource));

            CustomerDto customerDto = CustomerDto.builder()
                    .seq(1L)
                    .name("")
                    .nickname("")
                    .birthday(LocalDate.of(2000,12,23))
                    .email("")
                    .address("")
                    .phoneNumber("010-0232-2324")
                    .build();

            given(customerApiRepository.save(any())).willReturn(resource);
            customerApiService.modify(customerDto);

            verify(customerApiRepository).save(any());
        }
    }

    @Nested
    @DisplayName("정보를 삭제한다.")
    public class Delete{
        @Test
        @DisplayName("성공 : 정보 삭제")
        public void deleteData(){
            given(customerApiRepository.findBySeq(1L)).willReturn(java.util.Optional.ofNullable(resource));
            Customer customer = customerApiService.findBySeq(1L);
            customerApiService.deleteBySeq(1L);
            verify(customerApiRepository,times(2)).findBySeq(1L);
            verify(customerApiRepository).delete(customer);
        }

        @Test
        @DisplayName("실패 : 잘못된 요청 정보 삭제")
        public void deleteDataFail(){
            given(customerApiRepository.findBySeq(5L)).willThrow(new NoSuchDataException(5L));
            Assertions.assertThrows(NoSuchElementException.class,()->
                    customerApiService.deleteBySeq(5L));
        }
    }
}