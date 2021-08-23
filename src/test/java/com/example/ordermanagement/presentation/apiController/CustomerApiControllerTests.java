package com.example.ordermanagement.presentation.apiController;

import com.example.ordermanagement.application.service.CustomerApiService;
import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.domain.model.enumClass.RoleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerApiController.class)
@ExtendWith(SpringExtension.class)
class CustomerApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerApiService customerApiService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        createData();
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

        given(customerApiService.create(any())).willReturn(customer);
        given(customerApiService.findBySeq(1L)).willReturn(customer);
    }

    @Nested
    @DisplayName("user 정보를 조회한다.")
    public class Find{
        @Test
        @DisplayName("성공 : 단일 정보 조회")
        public void findBySeq() throws Exception {
            mockMvc.perform(get("/customer/1"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().string(
                            containsString("\"name\":\"aws동\"")))
                    .andExpect(content().string(
                            containsString("\"role\":\"BRONZE\"")))
                    .andExpect(content().string(
                            containsString("\"address\":\"서울 마포구\"")));

            verify(customerApiService).findBySeq(any());
        }
    }

    @Nested
    @DisplayName("user 정보를 저장한다.")
    public class save{
        @Test
        @DisplayName("성공 : 단일 정보 저장")
        public void createData() throws Exception {
            mockMvc.perform(post("/customer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"id\":\"aws1234\",\"password\":\"aws1234\"," +
                                    "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                    "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                    "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                    "\"role\":\"BRONZE\",\"grade\":2,\"registeredAt\":\"2002-12-23\"}"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().string(
                            containsString("\"name\":\"aws동\"")))
                    .andExpect(content().string(
                            containsString("\"role\":\"BRONZE\"")))
                    .andExpect(content().string(
                            containsString("\"address\":\"서울 마포구\"")));

            verify(customerApiService).create(any());
        }
    }
}

//{
//        "id":"aws1234",
//        "password":"aws1234",
//        "name":"aws동",
//        "nickname":"aws동이",
//        "birthday":"2012-05-23",
//        "phoneNumber":"010-0110-0220",
//        "email":"aws@naver.com",
//        "address":"서울 마포구",
//        "role":"GRANDMASTER",
//        "grade":2,
//        "registeredAt":"2002-12-23"
//        }