package com.example.ordermanagement.application.service;

import com.example.ordermanagement.domain.model.Address;
import com.example.ordermanagement.exception.NoSuchDataException;
import com.example.ordermanagement.persistance.repository.AddressApiRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("AddressApiServiceTests")
public class AddressApiServiceTests {

    private AddressApiService addressApiService;

    @Mock
    private AddressApiRepository addressApiRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        // Mock 어노테이션 변수들을 초기화한다. 가짜 객체를 만든다.
        mockAddressApiRepository();
        givenAllData();
        addressApiService = new AddressApiService(addressApiRepository);
    }

    private void mockAddressApiRepository(){
        Address address = Address.builder()
                .seq(1L)
                .content("서울 중랑구")
                .distinction(1)
                .build();

        given(addressApiRepository.findBySeq(1L)).willReturn(Optional.ofNullable(address));
    }

    private void givenAllData(){
        List<Address> addresses = new ArrayList<>();

        Address address = Address.builder()
                .seq(1L)
                .build();

        addresses.add(address);

        given(addressApiRepository.findAll()).willReturn(addresses);
    }

    @Nested
    @DisplayName("주어진 정보를 생성한다.")
    public class createData{

        @Test
        @DisplayName("성공")
        public void create(){

            Address resource = Address.builder()
                    .content("서울 중랑구")
                    .distinction(1)
                    .build();

            given(addressApiRepository.save(resource))
                    .willReturn(Address.builder()
                            .seq(5L)
                            .content("서울 중랑구")
                            .distinction(1)
                            .build());

            Address address = addressApiService.create(resource);

            Assertions.assertEquals(address.getSeq(),5L);
            Assertions.assertEquals(address.getContent(),resource.getContent());
            Assertions.assertEquals(address.getDistinction(),resource.getDistinction());

            verify(addressApiRepository,times(1)).save(any());
        }
    }

    @Nested
    @DisplayName("모든 정보를 조회한다.")
    public class FindAll{

        @Test
        @DisplayName("성공")
        public void findAll(){
            List<Address> addresses = addressApiService.findAll();
            Assertions.assertFalse(addresses.isEmpty());

            Address address = addresses.get(0);
            Assertions.assertEquals(address.getSeq(),1L);
        }
    }

    @Nested
    @DisplayName("단일 정보를 조회한다.")
    public class FindData{

        @DisplayName("성공")
        @Test
        public void findBySeq() {
            Address address = addressApiService.findBySeq(1L);

            Assertions.assertEquals(address.getSeq(), 1L);
            Assertions.assertEquals(address.getContent(), "서울 중랑구");
            Assertions.assertEquals(address.getDistinction(), 1);
        }

        @DisplayName("실패 - 에외처리 NoSuchDataException")
        @Test
        public void notFindBySeq() {
            Assertions.assertThrows(NoSuchDataException.class, () -> {
                addressApiService.findBySeq(10L);
            });
        }
    }

}