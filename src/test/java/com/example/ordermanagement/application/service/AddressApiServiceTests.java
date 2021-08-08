package com.example.ordermanagement.application.service;

import com.example.ordermanagement.domain.model.Address;
import com.example.ordermanagement.exception.NoSuchDataException;
import com.example.ordermanagement.persistance.repository.AddressApiRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

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

    @Nested
    @DisplayName("정보를 조회한다.")
    public class FindAddress{

        @DisplayName("성공 - 단일정보 조회.")
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