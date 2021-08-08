package com.example.ordermanagement.presentation.apiController;

import com.example.ordermanagement.exception.NoSuchDataException;
import com.example.ordermanagement.application.service.AddressApiService;
import com.example.ordermanagement.domain.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
//ApplicationContext를 만들고 관리하는 작업을 @RunWith(SpringRunner.class)에 설정된 class로 이용하겠다는 뜻
@WebMvcTest(AddressApiController.class)
@DisplayName("AddressApiControllerTests")
public class AddressApiControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressApiService addressApiService;

    @BeforeEach
    public void setUp(){
        givenAddress();
        givenError();
        givenAllData();
    }

    private void givenAddress(){
        Address address = Address.builder()
                .seq(1L)
                .content("서울 중랑구")
                .distinction(1)
                .build();
        given(addressApiService.findBySeq(1L))
                .willReturn(address);
    }

    private void givenError(){
        given(addressApiService.findBySeq(10L))
                .willThrow(new NoSuchDataException());
    }

    private void givenAllData(){
        List<Address> addresses = new ArrayList<>();
        for(Long i=1L; i<5L; i++){
            Address address = Address.builder()
                    .seq(i)
                    .build();
            addresses.add(address);
        }
        given(addressApiService.findAll()).willReturn(addresses);
    }

    @Nested
    @DisplayName("잘못된 정보 조회")
    public class NotDataApi{
        @Test
        @DisplayName("4xx 에러 반환")
        public void error() throws Exception {
            mvc.perform(get("/address/asklfjlsekjflse"))
                    .andExpect(status().is4xxClientError());
        }
    }

    @Nested
    @DisplayName("모든 정보를 조회한다.")
    public class FindAllData {
        @Test
        @DisplayName("성공")
        public void findAll() throws Exception {
            mvc.perform(get("/address/all"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("\"seq\":1")))
                    .andExpect(content().string(containsString("\"seq\":2")))
                    .andExpect(content().string(containsString("\"seq\":3")))
                    .andExpect(content().string(containsString("\"seq\":4")));
        }
    }

    @Nested
    @DisplayName("단일 정보를 조회한다.")
    public class FindData {

        @Test
        @DisplayName("성공")
        public void findBySeq() throws Exception {
            mvc.perform(get("/address/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                            containsString("\"content\":\"서울 중랑구\"")))
                    .andExpect(content().string(
                            containsString("\"distinction\":1")))
                    .andExpect(content().string(
                            containsString("\"seq\":1")));
        }

        @Test
        @DisplayName("잘못된 조회 - 4xx 에러")
        public void NotFindBySeq() throws Exception {
            mvc.perform(get("/address/10"))
                    .andExpect(status().is4xxClientError());
        }
    }


    @Nested
    @DisplayName("주어진 정보를 생성한다.")
    public class CreateData{
        @Test
        @DisplayName("성공")
        public void createData() throws Exception {
            given(addressApiService.create(any())).will(invocation -> {
               Address address = invocation.getArgument(0);
               return Address.builder()
                       .seq(5L)
                       .content(address.getContent())
                       .distinction(address.getDistinction())
                       .build();
            });

            mvc.perform(post("/address")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"content\":\"경기도 수원시\",\"distinction\":1}"))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location","/address/5"))
                    .andExpect(content().string("{}"));

            verify(addressApiService).create(any());
        }
    }
}